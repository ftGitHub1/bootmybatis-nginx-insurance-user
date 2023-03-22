package com.mapper;

import com.po.InsuranceUser;
import com.util.vo.ForgetPwVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InsuranceUserMapper {
    /**注册*/
    public  int insert(InsuranceUser user);
    /**测试账号是否存在  登录*/
    public InsuranceUser getByUserCode(String userCode);
    /** 激活账号*/
    public int activationUser(String userCode);
    /**修改密码*/
    public int forgetPw (String userCode,String passWd);
}
