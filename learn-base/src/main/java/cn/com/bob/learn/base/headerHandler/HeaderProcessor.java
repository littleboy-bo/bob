package cn.com.bob.learn.base.headerHandler;

import cn.com.bob.learn.base.BobContext;
import cn.com.bob.learn.base.constants.BobConstants;
import cn.com.bob.learn.base.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.*;

/**
 * springBoot 默认逻辑 返回前拦截 设置返回系统头
 * @author songbo
 * @date 2020-01-14
 */
@ControllerAdvice
public class HeaderProcessor implements ResponseBodyAdvice <Object> {
    private final static Logger log = LoggerFactory.getLogger(HeaderProcessor.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            String requestUri = serverHttpRequest.getURI().getPath();
            this.sendResponseHeader(serverHttpResponse, requestUri);
            ObjectMapper objectMapper = new ObjectMapper();
            if (StringUtils.isNotEmpty(requestUri) && !requestUri.contains("actuator/prometheus")) {
                log.debug("请求路径： {}， 响应体为 ：{} ", requestUri, objectMapper.writeValueAsString(o));
            }
        } catch (JsonProcessingException e ) {
            log.error(e.toString(), e);
        }

        return o;
    }

    private void sendResponseHeader(ServerHttpResponse response, String requestUri) throws JsonProcessingException {
        ServletServerHttpResponse res = (ServletServerHttpResponse)response;
        int httpStatus = res.getServletResponse().getStatus();
        Collection<String> headerNames = res.getServletResponse().getHeaderNames();
        Map bobHead = this.sendSuccessDefaultHeader(httpStatus, headerNames);
        if (bobHead != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String bobHeadStr = objectMapper.writeValueAsString(bobHead);
            response.getHeaders().add(BobConstants.SYS_HEAD, objectMapper.writeValueAsString(bobHead));
            log.debug("请求路径: {}, 响应头 BOB-Head:{}", requestUri, bobHeadStr);
            log.debug("请求路径: {}, 响应头状态码为 :{}", requestUri, httpStatus);
        }

    }

    public static Map sendSuccessDefaultHeader(int httpStatus, Collection<String> headerNames) {
        if (httpStatus >= 400 && httpStatus <= 500 || headerNames != null && headerNames.contains(BobConstants.SYS_HEAD)) {
            return null;
        } else {
            Map bobHead = BobContext.getInstance().getSysHead();

            List retArray = (List)bobHead.get("retArray");
            if (retArray == null || retArray.size() == 0) {
                List rets = new ArrayList();
                Map ret = new HashMap();
                ret.put("retCode", BobConstants.CODE_SUCCESS);
                ret.put("retMsg", BobConstants.MESSAGE_SUCCESS);
                rets.add(ret);
                bobHead.put("retArray", rets);
            }
            String currentRetStatus = (String)bobHead.get("retStatus");
            if (StringUtils.isEmpty(currentRetStatus)) {
                bobHead.put("retStatus", BobConstants.STATUS_SUCCESS);
            }

            return bobHead;
        }
    }

}
