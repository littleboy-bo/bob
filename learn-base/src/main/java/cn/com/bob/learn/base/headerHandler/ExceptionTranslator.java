package cn.com.bob.learn.base.headerHandler;

import cn.com.bob.learn.base.BobContext;
import cn.com.bob.learn.base.constants.BobConstants;
import cn.com.bob.learn.base.exception.BobException;
import cn.com.bob.learn.base.exception.ExceptionUtils;
import cn.com.bob.learn.base.exception.RootException;
import cn.com.bob.learn.base.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;

/**
 * springBoot 默认逻辑 Controller增强器 对全局 requestMapping 异常进行处理，当产生异常时对 BOB-Head 处理
 * @author songbo
 * @date 2020-01-14
 */
@ControllerAdvice
public class ExceptionTranslator {
    private final static Logger log = LoggerFactory.getLogger(ExceptionTranslator.class);

    public ExceptionTranslator(){

    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public void handlerMyException(MethodArgumentNotValidException ex, HttpServletResponse response) {
        log.error("MethodArgumentNotValidException ，Message:{}", ex.getMessage());
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        StringBuilder message = new StringBuilder();
        Iterator var5 = errors.iterator();

        while(var5.hasNext()) {
            FieldError error = (FieldError)var5.next();
            message.append(error.getField() + ":" + error.getDefaultMessage() + "  ");
        }

        this.setErrorResponseHeader(BobConstants.STATUS_UNKNOW, "T99200", message.toString(), response);
        log.error("响应状态码 HttpStatus:{}", HttpStatus.INTERNAL_SERVER_ERROR);
        log.error(ex.toString(), ex);
    }

    @ExceptionHandler({SQLException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void processSQLException(SQLException ex, HttpServletResponse response) {
        log.error("ZYBException ，Message:{}", ex.getMessage());
        this.setErrorResponseHeader(BobConstants.STATUS_FAILED, "T99100", ex.getMessage(), response);
        log.error("响应状态码 HttpStatus:{}", HttpStatus.OK);
        log.error(ex.toString(), ex);
    }

    @ExceptionHandler({RootException.class})
    @ResponseBody
    public void processBusiException(RootException ex, HttpServletResponse response) {
        log.error("BobException ，Message:{}", ex.getMessage());
        if ("X".equals(ex.getRetStatus())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            log.error("响应状态码 HttpStatus:{}", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            response.setStatus(HttpStatus.OK.value());
            log.error("响应状态码 HttpStatus:{}", HttpStatus.OK);
        }

        this.setErrorResponseHeader(ex.getRetStatus(), ex.getRetCode(), ex.getMessage(), response);
        log.error(ex.toString(), ex);
    }

/*    @ExceptionHandler({DuplicateKeyException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public void processPkError(DuplicateKeyException ex, HttpServletResponse response) {
        log.error("DuplicateKeyException Advice，Message:{}", ex.getMessage());
        this.setErrorResponseHeader(BobConstants.STATUS_UNKNOW, ErrcodeConstans.RET_CODE_DUPLICATE_KEY, ex.getMessage(), response);
        log.error("响应状态码 HttpStatus:{}", HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void processMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletResponse response) {
        log.error("HttpRequestMethodNotSupportedException ，Message:{}", ex.getMessage());
        this.setErrorResponseHeader(BobConstants.STATUS_UNKNOW, "T99000", ex.toString(), response);
        log.error("响应状态码 HttpStatus:{}", HttpStatus.INTERNAL_SERVER_ERROR);
        log.error(ex.toString(), ex);
    }

    @ExceptionHandler({Exception.class})
    public void processRuntimeException(Exception ex, HttpServletResponse response) {
        String errorMsg = StringUtils.isNotEmpty(ex.getMessage()) ? ex.getMessage() : ex.getClass().getSimpleName();
        log.error("Exception Advice，Code:{}，Message:{}", HttpStatus.INTERNAL_SERVER_ERROR, errorMsg);
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof BobException) {
            BobException bobException = (BobException)rootCause;
            if ("X".equals(bobException.getRetStatus())) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                log.error("响应状态码 HttpStatus:{}", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                response.setStatus(HttpStatus.OK.value());
                log.error("响应状态码 HttpStatus:{}", HttpStatus.OK);
            }

            this.setErrorResponseHeader(bobException.getRetStatus(), bobException.getRetCode(), bobException.getMessage(), response);
            log.error(bobException.toString(), rootCause);
        } else {
            this.setErrorResponseHeader(BobConstants.STATUS_UNKNOW, "T99000", errorMsg, response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            log.error("响应状态码 HttpStatus:{}", HttpStatus.INTERNAL_SERVER_ERROR);
            log.error(rootCause.toString(), rootCause);
        }
    }

    private void setErrorResponseHeader(String retStatus, String retCode, String retMsg, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        Map headerMap = BobContext.getInstance().getSysHead();
        
        List rets = new ArrayList();
        HashMap ret = new HashMap();

        try {
            if (retMsg != null) {
                if (log.isDebugEnabled()) {
                    log.debug("打印返回的错误信息 retMsg:" + retMsg);
                }

                if (retMsg.startsWith("[")) {
                    try {
                        new ArrayList();
                        List<Map<String, Object>> retsObj = (List)mapper.readValue(retMsg, List.class);
                        Iterator var21 = retsObj.iterator();

                        while(true) {
                            if (!var21.hasNext()) {
                                rets.addAll(retsObj);
                                break;
                            }

                            Map<String, Object> map = (Map)var21.next();
                            Iterator var12 = map.keySet().iterator();

                            while(var12.hasNext()) {
                                String key = (String)var12.next();
                                String value = String.valueOf(map.get(key));
                                if (value.equals(URLDecoder.decode(value, "UTF-8"))) {
                                    String retMsgItem = URLEncoder.encode(value, "UTF-8");
                                    retMsgItem.replaceAll("\\+", "%20");
                                    map.put(key, retMsgItem);
                                }
                            }
                        }
                    } catch (Exception var18) {
                        if (retMsg.equals(URLDecoder.decode(retMsg, "UTF-8"))) {
                            retMsg = URLEncoder.encode(retMsg, "UTF-8");
                            retMsg = retMsg.replaceAll("\\+", "%20");
                        }

                        ret.put("retMsg", retMsg);
                        ret.put("retCode", retCode);
                        rets.add(ret);
                    }
                } else {
                    if (retMsg.equals(URLDecoder.decode(retMsg, "UTF-8"))) {
                        retMsg = URLEncoder.encode(retMsg, "UTF-8");
                        retMsg = retMsg.replaceAll("\\+", "%20");
                    }

                    ret.put("retMsg", retMsg);
                    ret.put("retCode", retCode);
                    rets.add(ret);
                }
            } else {
                retMsg = "";
                ret.put("retMsg", retMsg);
                ret.put("retCode", retCode);
                rets.add(ret);
            }

            headerMap.put("retArray", rets);
            headerMap.put("retStatus", retStatus);
        } catch (Exception var19) {
            log.error(var19.toString(), var19);
            String msg = "异常信息包含特殊字符解析异常";

            try {
                ret.put("retMsg", URLEncoder.encode(msg, "UTF-8"));
            } catch (Exception var17) {
                log.error(var17.toString(), var17);
            }

            ret.put("retCode", "T99000");
            rets.add(ret);
            headerMap.put("retArray", rets);
            headerMap.put("retStatus", BobConstants.STATUS_UNKNOW);
        }

        try {
            String bobHead = mapper.writeValueAsString(headerMap);
            response.setHeader(BobConstants.SYS_HEAD, bobHead);
            log.error("响应头 BOB-Head:{}", bobHead);
        } catch (Exception e) {
            log.error(e.toString(), e);
        }

    }
}
