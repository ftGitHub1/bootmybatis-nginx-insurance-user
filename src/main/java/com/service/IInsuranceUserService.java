package com.service;

import com.po.InsuranceUser;

public interface IInsuranceUserService {
    public  int insert(InsuranceUser user);
    /**测试账号是否存在*/

    public InsuranceUser getByUserCode(String userCode);
    /**激活*/
    public int activationUser(String userCode);
    /**修改密码*/
    public int forgetPw (String userCode,String passWd);
}
