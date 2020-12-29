package cn.com.bob.base.exception;

import cn.com.bob.base.bean.Errcode;
import cn.com.bob.base.constants.ErrCodeConstants;
import cn.com.bob.base.constants.ErrCodeConstants;
import cn.com.bob.base.bean.Errcode;
import org.slf4j.helpers.MessageFormatter;

public class RootException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String level;
    private String retStatus;
    private String retCode;
    private String retMsg;

    public RootException(String message) {
        this(ErrCodeConstants.MSG_LEVEL_ERROR, ErrCodeConstants.RET_STATUS_TRANS_X, ErrCodeConstants.RET_CODE_EXCEPTION, message);
    }

    public RootException(Errcode message, Object... args) {
        this(ErrCodeConstants.MSG_LEVEL_ERROR, ErrCodeConstants.RET_STATUS_TRANS_X, message.getCode(), message.getMessage(), (Throwable)null, args);
    }

    public RootException(String retStatus, String retCode, String retMsg, Object... args) {
        this(ErrCodeConstants.MSG_LEVEL_ERROR, retStatus, retCode, retMsg, args);
    }

    public RootException(String level, String retStatus, String code, String retMsg, Object... args) {
        this(level, retStatus, code, retMsg, (Throwable)null, args);
    }

    public RootException(String retStatus, String retCode, String retMsg, Throwable cause, Object... args) {
        this(ErrCodeConstants.MSG_LEVEL_ERROR, retStatus, retCode, retMsg, cause, args);
    }

    public RootException(String level, String retStatus, String retCode, String retMsg, Throwable cause, Object... args) {
        super(MessageFormatter.arrayFormat(retMsg, args).getMessage(), cause);
        this.retStatus = ErrCodeConstants.RET_STATUS_TRANS_X;
        this.retMsg = MessageFormatter.arrayFormat(retMsg, args).getMessage();
        this.level = level;
        this.retStatus = retStatus;
        this.retCode = retCode;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRetStatus() {
        return this.retStatus;
    }

    public void setRetStatus(String retStatus) {
        this.retStatus = retStatus;
    }

    public String getRetCode() {
        return this.retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return this.retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getMsg() {
        Throwable tmp = this;

        String fullMsg;
        for(fullMsg = ""; tmp != null; tmp = ((Throwable)tmp).getCause()) {
            if (tmp instanceof RootException) {
                RootException b = (RootException)tmp;
                fullMsg = fullMsg + b.retMsg + "\r\n";
            }
        }

        return fullMsg;
    }
}
