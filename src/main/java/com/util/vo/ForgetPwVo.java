package com.util.vo;

public class ForgetPwVo {
    private String userCode;//若是管理员分配，系统将自动生成唯一账号；自注册用户则为邮箱或者手机号
    private String userPassword;
    private String code;//验证码

    @Override
    public String toString() {
        return "ForgetPwVo{" +
                "userCode='" + userCode + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getUserCode() {
        return userCode;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ForgetPwVo(String userCode, String userPassword, String code) {
        this.userCode = userCode;
        this.userPassword = userPassword;
        this.code = code;
    }

    public ForgetPwVo() {
    }
}
