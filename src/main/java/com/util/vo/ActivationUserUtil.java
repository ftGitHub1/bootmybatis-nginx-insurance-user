package com.util.vo;

import java.io.Serializable;

public class ActivationUserUtil implements Serializable {
    private String userCode;
    private  String code;

    @Override
    public String toString() {
        return "ActivationUserUtil{" +
                "userCode='" + userCode + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ActivationUserUtil() {
    }

    public ActivationUserUtil(String userCode, String code) {
        this.userCode = userCode;
        this.code = code;
    }
}
