package cn.com.bob.learn.base.bean;


public class Errcode {
    private String code;
    private String message;
    private String level;
    private String detailMessage;

    public Errcode() {
    }

    public Errcode(String code, String message, String level) {
        this.code = code;
        this.message = message;
        this.level = level;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailMessage() {
        return this.detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    @Override
    public String toString() {
        return "Message [code=" + this.code + ", message=" + this.message + ", level=" + this.level + ", detailMessage=" + this.detailMessage + "]";
    }
}