package com.po;

import java.io.Serializable;

/**
 * 角色菜单中间实体类
 * */
public class InsuranceRoleMenu implements Serializable {
    private Integer  roleid ;//角色主键
    private Integer menuid;//菜单主键
    public int InsuranceRoleMenu(){
        return 1;
    };

    public InsuranceRoleMenu() {
    }

    public InsuranceRoleMenu(Integer roleid, Integer menuid) {
        this.roleid = roleid;
        this.menuid = menuid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

    @Override
    public String toString() {
        return "InsuranceRoleMenu{" +
                "roleid=" + roleid +
                ", menuid=" + menuid +
                '}';
    }
}
