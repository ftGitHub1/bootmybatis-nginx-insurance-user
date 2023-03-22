package com.util.vo;

import java.io.Serializable;

public class CheckTokenUtil implements Serializable {
    private String userCode;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Override
    public String toString() {
        return "CheckTokenUtil{" +
                "userCode='" + userCode + '\'' +
                '}';
    }

    public CheckTokenUtil(String userCode) {
        this.userCode = userCode;
    }

    public CheckTokenUtil() {
    }
}
