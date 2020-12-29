package cn.com.bob.base.servlet;


import cn.com.bob.base.BobContext;
import cn.com.bob.base.constants.BobConstants;
import cn.com.bob.base.util.DateUtils;
import cn.com.bob.base.util.MdcUtils;
import cn.com.bob.base.util.SeqUtils;
import cn.com.bob.base.util.StringUtils;
import cn.com.bob.base.BobContext;
import cn.com.bob.base.constants.BobConstants;
import cn.com.bob.base.exception.ExceptionUtils;
import cn.com.bob.base.handle.BobHandler;
import cn.com.bob.base.headerHandler.HeaderProcessor;
import cn.com.bob.base.thread.LocalThreadManager;
import cn.com.bob.base.util.DateUtils;
import cn.com.bob.base.util.MdcUtils;
import cn.com.bob.base.util.SeqUtils;
import cn.com.bob.base.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author songbo
 * @date 2020-01-10
 * bob-learn 统一 过滤器
 */
public class BobFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(BobFilter.class);

/*    private BobHandler bobHandler;

    public BobFilter(BobHandler bobHandler) {
        if (log.isDebugEnabled()) {
            log.debug("BobFilter is registry......");
        }
        this.bobHandler = bobHandler;
    }*/

    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("BobFilter.init I should be alive ...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            long start = System.currentTimeMillis();
            log.debug("BobFilter.doFilter start ...");
            Map result = null;
            Map requestMap = null;
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String requestURI = request.getRequestURI();

            Enumeration<String> requestHeaderNames = request.getHeaderNames();
            log.debug("请求路径 ： {} ", requestURI);
            while (requestHeaderNames.hasMoreElements()) {
                String requestHeaderName = (String) requestHeaderNames.nextElement();
                String header = request.getHeader(requestHeaderName);
                log.debug("headerName : " + requestHeaderName + ", 值为：" + header);
            }
            // 初始化系统头 BOB-Head
            requestMap = this.initHeader(request);
            //初始化线程变量、上下文
            LocalThreadManager.put(BobConstants.SEQ_NO, requestMap.get(BobConstants.SEQ_NO));
            LocalThreadManager.setUID(SeqUtils.getStringSeq());
            BobContext.getInstance().setSysHead(requestMap);
            MdcUtils.init();
            if (log.isDebugEnabled()) {
                log.debug("uri[" + request.getRequestURI() + "] is started");
            }
            try {
                String tranCode = getTranCodeByUri(request.getRequestURI());
                //  TODO tranCode 不为空，走执行自己实现的逻辑，待后续实现
                // tranCode 为空，执行SpringBoot默认逻辑
                if (StringUtils.isNotEmpty(tranCode)) {
                    // TODO 调用自己实现的流程逻辑
                    //String inMsg = (String) LocalThreadManager.get(GravityConstants.IN_MSG);
                    String inMsg = this.getRequestMessage(request);
                    requestMap = getMapByString(inMsg, requestMap);
                    //result = bobHandler.handle(tranCode, requestMap);
                } else {
                    //调用SpringBoot默认rest流程逻辑
                    log.debug("调用默认逻辑框架开始...");
                    filterChain.doFilter(servletRequest, servletResponse);

                    log.debug("调用默认逻辑框架结束...");
                    log.debug("server exec time ：" + (System.currentTimeMillis() - start));
                }

            } catch (Throwable t) {
                if (log.isErrorEnabled()) {
                    log.error("execution exception: " + ExceptionUtils.getStackTrace(t));
                }
                result = new HashMap();
                result.put(BobConstants.SYS_HEAD, setErrorResponseHeader(t, requestMap, false));

            } finally {
                writeResponse(result, response);
                LocalThreadManager.remove();
                BobContext.clear();
                MdcUtils.clear();
            }
            log.debug("BobFilter.doFilter end ...");
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {
        log.debug("BobFilter.destroy I should be dead ...");
    }

    /**
     * BOB-Head（系统头）初始化
     * @param request
     * @return
     * @throws IOException
     */
    private Map initHeader(HttpServletRequest request) throws IOException {

        long startTime = System.currentTimeMillis();
        String requestHeaderStr = request.getHeader(BobConstants.SYS_HEAD);

        log.debug("reqeust.Header.head:[{}]", requestHeaderStr);

        String tellerId = "";
        String seqNo = "";
        String repeatFlag = "";
        String tranDate = "";
        String tranTime = "";
        String userLang = "";
        if (!StringUtils.isBlank(requestHeaderStr)) {
            ObjectMapper mapper = new ObjectMapper();
            Map requestHeader = (Map) mapper.readValue(requestHeaderStr, Map.class);
            if (requestHeader.containsKey("seqNo")) {
                seqNo = (String) requestHeader.get("seqNo");
            }

            if (requestHeader.containsKey("repeatFlag")) {
                repeatFlag = (String) requestHeader.get("repeatFlag");
            }

            if (requestHeader.containsKey("tellerId")) {
                tellerId = (String) requestHeader.get("tellerId");
            }

            if (requestHeader.containsKey("userLang")) {
                userLang = (String) requestHeader.get("userLang");
            }

        }

        Map currentBobHeader = new HashMap();
        Date currentDate = new Date();
        if (StringUtils.isEmpty(tranDate)) {
            tranDate = DateUtils.getDate();
        }

        if (StringUtils.isEmpty(tranTime)) {
            // HHmmssSSS
            tranTime = DateUtils.getDateTime(currentDate, DateUtils.PATTERN_SIMPLE_MILLISECOND);
        }

        if (StringUtils.isEmpty(userLang)) {
            userLang = "CHINESE";
        }

        currentBobHeader.put("tranDate", tranDate);
        currentBobHeader.put("tranTime", tranTime);
        currentBobHeader.put("repeatFlag", repeatFlag);
        currentBobHeader.put("seqNo", seqNo);
        currentBobHeader.put("tellerId", tellerId);
        currentBobHeader.put("userLang", userLang);
        Map requestHeader = new HashMap();
        log.debug("系统头初始化完成 BOB-Head[{}]....", currentBobHeader);
        log.debug("线程" + Thread.currentThread().getId() + ",initHeader 耗時：" + (System.currentTimeMillis() - startTime));
        //requestHeader.put(BobConstants.SYS_HEAD, currentBobHeader);
        return currentBobHeader;
    }

    /**
     * 设置未知异常输出
     * @param t
     * @param result
     * @param isEncoderMsg
     * @return Map
     * @throws UnsupportedEncodingException
     */
    private Map setErrorResponseHeader(Throwable t, Map result, boolean isEncoderMsg) throws UnsupportedEncodingException {
        Map sysHead = new HashMap();
        if (result != null && result.get(BobConstants.SYS_HEAD) != null) {
            sysHead = (Map) result.get(BobConstants.SYS_HEAD);
        }
        List<Map> rets = new ArrayList<Map>();
        Map ret = new HashMap();
        ret.put(BobConstants.RET_CODE, "999999");
        if (isEncoderMsg) {
            ret.put(BobConstants.RET_MSG, t.getMessage() == null ? "" : URLEncoder.encode(t.getMessage(), BobConstants.CHARSET));
        } else {
            ret.put(BobConstants.RET_MSG, t.getMessage());
        }

        rets.add(ret);
        sysHead.put(BobConstants.RETS, rets);
        sysHead.put(BobConstants.RET_STATUS, BobConstants.STATUS_FAILED);
        return sysHead;

    }

    /**
     * 设置成功系统头
     * @param response
     * @param map
     */
    private void setResponseHeader(HttpServletResponse response, Map map) {

        List<Map> rets = new ArrayList<Map>();
        Map ret = new HashMap();
        ret.put(BobConstants.RET_CODE, BobConstants.CODE_SUCCESS);
        ret.put(BobConstants.RET_MSG, BobConstants.MESSAGE_SUCCESS);
        rets.add(ret);
        map.put(BobConstants.RETS, rets);
        map.put(BobConstants.RET_STATUS, BobConstants.STATUS_SUCCESS);
        response.setHeader(BobConstants.SYS_HEAD, JSONObject.toJSONString(map));

    }

    /**
     * 根据系统头信息设置响应状态码，默认设置HttpServletResponse.SC_OK
     * @param response
     */
    private void setResponseStatus(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * 根据系统头信息设置响应状态码，默认设置HttpServletResponse.SC_INTERNAL_SERVER_ERROR
     * @param response
     */
    private void setErrResponseStatus(HttpServletResponse response) {
        // HttpStatus.INTERNAL_SERVER_ERROR
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * 向response写输出,只有当执行自定义框架逻辑时（result 不为空）或返回为null 会走进实际逻辑中
     * @param result
     * @param response
     * @throws ServletException
     * @throws UnsupportedEncodingException
     */
    private void writeResponse(Map result, HttpServletResponse response) throws ServletException, UnsupportedEncodingException, IOException {
        Map sysHead;
        if (result != null) {
            try {
                sysHead = (Map) result.get(BobConstants.SYS_HEAD);
                List<Map> rets = (List<Map>) sysHead.get(BobConstants.RETS);
                for (Map ret : rets) {
                    try {
                        if (String.valueOf(ret.get(BobConstants.RET_MSG)).equals(URLDecoder.decode(String.valueOf(ret.get(BobConstants.RET_MSG)), BobConstants.CHARSET))) {
                            String retMsg = URLEncoder.encode(String.valueOf(ret.get(BobConstants.RET_MSG)), BobConstants.CHARSET);
                            if (retMsg != null) {
                                retMsg = retMsg.replaceAll("\\+", "%20");
                            }
                            ret.put(BobConstants.RET_MSG, retMsg);
                        } else {
                            String retMsg = String.valueOf(ret.get(BobConstants.RET_MSG));
                            if (retMsg != null) {
                                retMsg = retMsg.replaceAll("\\+", "%20");
                            }
                            ret.put(BobConstants.RET_MSG, retMsg);
                        }
                    } catch (UnsupportedEncodingException t) {
                        String retMsg = URLEncoder.encode(String.valueOf(ret.get(BobConstants.RET_MSG)), BobConstants.CHARSET);
                        if (retMsg != null) {
                            retMsg = retMsg.replaceAll("\\+", "%20");
                        }
                        ret.put(BobConstants.RET_MSG, retMsg);
                    }
                }
                if (log.isDebugEnabled()) {
                    log.debug("请求路径: {}, 响应头 BOB-Head:{}, 响应头状态码为 :{}", "111111", JSONObject.toJSONString(sysHead), 200);
                }
                setResponseStatus(response);
                response.setCharacterEncoding(BobConstants.CHARSET);
                response.setContentType(MediaType.APPLICATION_JSON.toString());
                response.setHeader(BobConstants.SYS_HEAD, JSONObject.toJSONString(sysHead));

                // 安全相关的http头规范
                response.setHeader("X-Content-Type-Options", "nosniff");
                response.setHeader("X-Xss-Protection", "mode=block");
                response.setHeader("X-Frame-Options", "deny");
                response.setHeader("Content-Security-Policy", "default-src 'none'");

                result.remove(BobConstants.SYS_HEAD);
                if (result.size() > 0) {
                    OutputStream os = response.getOutputStream();
                    os.write(JSONObject.toJSONString(result).getBytes(BobConstants.CHARSET));
                }
            } catch (Throwable t) {
                if (log.isErrorEnabled()) {
                    log.error("execution exception: " + t.getMessage(), t);
                }
                //setErrResponseStatus(response);
                response.setHeader(BobConstants.SYS_HEAD, JSONObject.toJSONString(setErrorResponseHeader(t, result, true)));
            }
        }else{
            try {
                int httpStatus = response.getStatus();
                Collection<String> headerNames = response.getHeaderNames();
                Map bobHead = HeaderProcessor.sendSuccessDefaultHeader(httpStatus,headerNames);

                response.setCharacterEncoding(BobConstants.CHARSET);
                response.setContentType(MediaType.APPLICATION_JSON.toString());
                if (bobHead != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String bobHeadStr = objectMapper.writeValueAsString(bobHead);
                    response.setHeader(BobConstants.SYS_HEAD, objectMapper.writeValueAsString(bobHead));
                    log.debug("响应头 BOB-Head:{}", bobHeadStr);
                    log.debug("响应头状态码为 :{}", httpStatus);
                }
            }catch (Exception e){
                throw e ;
            }

        }
    }

    /**
     * 通过request获取入参字符串
     * @param request
     * @return
     * @throws IOException
     */
    private String getRequestMessage(HttpServletRequest request) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytes;
        try (InputStream in = request.getInputStream()) {
            while ((bytes = in.read(buffer, 0, 8192)) != -1) {
                baos.write(buffer, 0, bytes);
            }
            byte[] b = baos.toByteArray();
            return new String(b, BobConstants.CHARSET);
        }
    }

    /**
     * 根据inMsg转换为map
     * @param inMsg
     * @param requestMap
     * @return
     */
    private Map getMapByString(String inMsg,Map requestMap) {
        Map in = JSONObject.parseObject(inMsg);
        if (in == null) {
            in = new HashMap();
        }
        in.put(BobConstants.SYS_HEAD,requestMap);
        return in;
    }

    /**
     * TODO 待实现
     * 通过uri获取流程id
     * @param uri
     * @return
     */
    private String getTranCodeByUri(String uri) {
        /*FlowFactory flowFactory = SpringApplicationContext.getContext().getBean(FlowFactory.class);
        FlowModel flowModel = flowFactory.getFlowByUri(uri);
        if (flowModel == null){
            return null;
        }
        return flowModel.getId();*/
        return null;
    }

}
