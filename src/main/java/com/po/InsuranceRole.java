package com.po;

import java.io.Serializable;

/**
 * 角色实体类
 * */
public class InsuranceRole implements Serializable {
    private Integer id;//角色编号
    private String rname;//角色名称

    public InsuranceRole() {
    }

    public InsuranceRole(Integer id, String rname) {
        this.id = id;
        this.rname = rname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    @Override
    public String toString() {
        return "InsuranceRole{" +
                "id=" + id +
                ", rname='" + rname + '\'' +
                '}';
    }
}
