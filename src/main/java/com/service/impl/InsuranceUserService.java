package com.service.impl;

import com.mapper.InsuranceUserMapper;
import com.po.InsuranceUser;
import com.service.IInsuranceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Action;

@Service
@Transactional
public class InsuranceUserService implements IInsuranceUserService {
    @Autowired
    private InsuranceUserMapper insuranceUserMapper;
    @Override
    public int insert(InsuranceUser user) {

        return insuranceUserMapper.insert(user);
    }

    @Override
    public InsuranceUser getByUserCode(String userCode) {
        return insuranceUserMapper.getByUserCode(userCode);
    }

    @Override
    public int activationUser(String userCode) {
        return insuranceUserMapper.activationUser(userCode);
    }
    @Override
    public int forgetPw(String userCode,String passWd) {
        return insuranceUserMapper.forgetPw(userCode,passWd);
    }

}
