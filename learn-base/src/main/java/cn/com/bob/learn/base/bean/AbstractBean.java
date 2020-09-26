package cn.com.bob.learn.base.bean;

import java.io.Serializable;

public abstract class AbstractBean implements Serializable {

    private static final long serialVersionUID = -5521527686619235184L;

    private String firstUpper(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}