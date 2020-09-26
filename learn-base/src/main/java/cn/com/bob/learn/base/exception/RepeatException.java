package cn.com.bob.learn.base.exception;


import cn.com.bob.learn.base.BobResult;
import cn.com.bob.learn.base.bean.result.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RepeatException extends BusinessException {

    private static final Logger logger = LoggerFactory.getLogger(BobResult.class);

    private String retStatus;

    private Results rs;

    private Map out;

    public RepeatException(String retStatus, String retCode, String retMsg, Map out){
        super(retCode,retMsg);
        this.retStatus = retStatus;
        this.rs = new Results(retCode,retMsg);
        this.out = out;
    }

    public String getRetStatus() {
        return retStatus;
    }

    public Results getRs() {
        return rs;
    }

    public Map getOut() {
        return out;
    }
}
