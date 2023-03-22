package com.util.vo;

import java.io.Serializable;

public class InsuranceUserAddvo implements Serializable {
    private String userCode;//若是管理员分配，系统将自动生成唯一账号；自注册用户则为邮箱或者手机号
    private String userPassword; //若是管理员分配，系统将自动生成唯一账号；自注册用户则为自定义密码
    private String userName;//用户真实姓名',
    private String weChat;//微信号(手机或邮箱)
    private String idnumber;//身份证号

    public InsuranceUserAddvo() {
    }

    public InsuranceUserAddvo(String userCode, String userPassword, String userName, String weChat, String idnumber) {
        this.userCode = userCode;
        this.userPassword = userPassword;
        this.userName = userName;
        this.weChat = weChat;
        this.idnumber = idnumber;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    @Override
    public String toString() {
        return "InsuranceUserAddvo{" +
                "userCode='" + userCode + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userName='" + userName + '\'' +
                ", weChat='" + weChat + '\'' +
                ", idnumber='" + idnumber + '\'' +
                '}';
    }
}
