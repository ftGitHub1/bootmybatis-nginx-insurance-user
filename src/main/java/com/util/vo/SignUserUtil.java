package com.util.vo;

import java.io.Serializable;

public class SignUserUtil  implements Serializable {
    private String userCode;
    private String userPassword;

    public String getUserCode() {
        return userCode;
    }

    @Override
    public String toString() {
        return "SignUserUtil{" +
                "userCode='" + userCode + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public SignUserUtil(String userCode, String userPassword) {
        this.userCode = userCode;
        this.userPassword = userPassword;
    }

    public SignUserUtil() {
    }
}
