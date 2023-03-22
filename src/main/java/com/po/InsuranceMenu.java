package com.po;

import java.io.Serializable;

/**
 * 菜单实体类
 * */
public class InsuranceMenu implements Serializable {
    private Integer id;//菜单编号
    private String pid;//父级Id
    private String textcontet;//菜单内容
    private String url;//'菜单对应页面路径
    public InsuranceMenu() {
    }

    public InsuranceMenu(Integer id, String pid, String textcontet, String url) {
        this.id = id;
        this.pid = pid;
        this.textcontet = textcontet;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTextcontet() {
        return textcontet;
    }

    public void setTextcontet(String textcontet) {
        this.textcontet = textcontet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "InsuranceMenu{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", textcontet='" + textcontet + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
