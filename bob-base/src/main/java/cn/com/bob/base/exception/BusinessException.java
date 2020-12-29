package cn.com.bob.base.exception;


import cn.com.bob.base.bean.result.Result;
import cn.com.bob.base.bean.result.Results;
import cn.com.bob.base.constants.BobConstants;
import cn.com.bob.base.bean.result.Result;
import cn.com.bob.base.bean.result.Results;
import cn.com.bob.base.constants.BobConstants;
import com.alibaba.fastjson.JSON;

/**
 * 业务异常
 *
 * @author songbo
 */
public class BusinessException extends BobException {

    private Results rets = new Results();

    private static final long serialVersionUID = -5533270369609879192L;

    private String businessMessage;

    public String getBusinessMessage() {
        return businessMessage;
    }

    public void setBusinessMessage(String businessMessage) {
        this.businessMessage = businessMessage;
    }

    public BusinessException(Results rets) {
        super(JSON.toJSONString(rets.getRets()));
        businessMessage = getMessage();
        this.rets = rets;
    }

    public BusinessException(Result ret) {
        super(JSON.toJSONString(new Results(ret)));
        businessMessage = getMessage();
        this.rets.addResult(ret);
    }

    public BusinessException(String code, String msg) {
        super(JSON.toJSONString(new Results().addResult(new Result(code, msg)).getRets()));
        businessMessage = msg;
        this.rets.addResult(new Result(code, msg));
    }

    public BusinessException(Results rets, Throwable cause) {
        super(JSON.toJSONString(rets.getRets()), cause);
        businessMessage = getMessage();
        this.rets = rets;
    }

    public BusinessException(Result ret, Throwable cause) {
        super(JSON.toJSONString(new Results().addResult(ret).getRets()), cause);
        businessMessage = getMessage();
        this.rets.addResult(ret);
    }

    public BusinessException(String code, String msg, Throwable cause) {
        super(JSON.toJSONString(new Results().addResult(new Result(code, msg)).getRets()), cause);
        businessMessage = getMessage();
        this.rets.addResult(new Result(code, msg));
    }

    public Results getRets() {
        return rets;
    }

    public void setRets(Results rets) {
        this.rets = rets;
    }

    @Override
    public String getRetStatus(){
        return BobConstants.STATUS_FAILED;
    }
}