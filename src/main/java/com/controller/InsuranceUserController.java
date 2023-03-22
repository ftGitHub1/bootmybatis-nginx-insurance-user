package com.controller;

import com.po.Dto;
import com.po.InsuranceUser;
import com.service.IInsuranceUserService;

import com.util.*;
import com.util.qwe.MD5Util;
import com.util.vo.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.PreDestroy;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping(value = "/api")
public class InsuranceUserController {
    @Autowired
    private IInsuranceUserService iInsuranceUserService;
    private Jedis jedis=new Jedis("127.0.0.1",6379);

    @RequestMapping(value = "/registerByPhone")
    public Dto codeSave(HttpServletRequest request , HttpServletResponse response , @RequestBody InsuranceUserAddvo userAddvo){
        System.out.println("注册方法:==>"+userAddvo.toString());
    // 测试该账号是否存在
        InsuranceUser insuranceUser = iInsuranceUserService.getByUserCode(userAddvo.getUserCode());
    if(insuranceUser != null){
        /**注册过*/
        System.out.println("注册过");
        return null;
    }else {
        /**没有注册*/
        System.out.println("可以注册");
        InsuranceUser user = new InsuranceUser();
        user.setUserCode(userAddvo.getUserCode());
        /**md5是一个网络上的一个加密，不饿能够做为工作上的加密未见**/
        user.setUserPassword(MD5Util.getMd5(userAddvo.getUserPassword(),32));
        user.setUserType(1);
        user.setUserName(userAddvo.getUserName());
        user.setWeChat(userAddvo.getWeChat());
        user.setIdnumber(userAddvo.getIdnumber());
        user.setCreationDate(new Date());
        user.setActivated(0);
        /**对象创建完成，进行添加*/
        int code = iInsuranceUserService.insert(user);
        if(code > 0 ){
            //激活账号
                //判断是手机号还是邮箱
            if(user.getUserCode().indexOf("@") != -1){
                System.out.println("这个是邮箱发送");
                String smssk = EmailUtil.getEmailSend(user);
                System.out.println(smssk);
                jedis.setex(user.getUserCode(),120,smssk);
            }else{
                System.out.println("这个是短信发送");
                String smssk = SmsUtil.getSmsSeng(user);
                System.out.println(smssk);
                jedis.setex(user.getUserCode(),120,smssk);
            }
            return DtoUtil.returnSuccess("注册成功，前往激活页面");

        }else{
            return  DtoUtil.returnFail("注册失败", ErrorCode.AUTH_UNKNOWN);
        }
    }


    }
    @RequestMapping(value = "/activation")
    public Dto activation (HttpServletRequest request , HttpServletResponse response ,@RequestBody ActivationUserUtil user){
        System.out.println("激活方法");
        System.out.println(user.toString());

        /**查询是否激活big**/
        InsuranceUser iuser = iInsuranceUserService.getByUserCode(user.getUserCode());
        if(iuser ==null){
            System.out.println("111111");
            return  DtoUtil.returnFail("激活失败,账号不正确", ErrorCode.AUTH_AUTHENTICATION_FAILED);
        }else if(iuser.getActivated() != 0){
            System.out.println("2222222");
            return DtoUtil.returnSuccess("该账号已经激活过了");
        }else{
            /***激活bgin**/
            System.out.println("333333");
            String smssk = jedis.get(user.getUserCode());

            if(smssk.equals(user.getCode())){
                iInsuranceUserService.activationUser(user.getUserCode());
                System.out.println(smssk);
                return DtoUtil.returnSuccess("激活成功，前往登录页面");
            }else{
                return  DtoUtil.returnFail("激活失败,验证码不正确", ErrorCode.AUTH_AUTHENTICATION_FAILED);
        }

        }
        /**查询是否激活end*/

    }
/**登录*/
    @RequestMapping(value = "/sign")
    public Dto sign(HttpServletRequest request , HttpServletResponse response , @RequestBody SignUserUtil signUser){

        System.out.println("sign");
        InsuranceUser user =  iInsuranceUserService.getByUserCode(signUser.getUserCode());
        /****判断账号是否存在*/
        System.out.println("user:"+user.toString());
        if(user == null){
            System.out.println("123");
            return DtoUtil.returnFail("登录失败，账号未注册",ErrorCode.AUTH_USER_ALREADY_EXISTS);
        }else{
            /**进行登录操作**/
            /**判断是否激活**/
            String rToken = jedis.get(user.getUserCode());
            if(user.getActivated()==1){
                /**激活**/
                if(user.getUserPassword().equals(MD5Util.getMd5(signUser.getUserPassword(),32))){
                    //判断是否处于登录状太
                    if(rToken == null  || rToken.length()==4){
                        /**未登录*/
                        System.out.println("meiyoudengluguo");
                    }else{
                        /**登录*/
                        System.out.println("denglu........"+rToken);
                        if(TokenUtil.validateUserAgent(request.getHeader("User-Agent"),rToken)){
                            /**是同一个浏览器登录**/
                            System.out.println("tongyigeliulaqi");
                        }else{
                            /** 不同浏览器*/
                            try {
                                System.out.println("butongliulanqi");
                                TokenUtil.replaceToken(request.getHeader("User-Agent"),rToken,user);
                            } catch (TokenValidationFailedException e) {
                                return DtoUtil.returnFail(e.getMessage(), "14780");                            }
                        }
                    }
                    System.out.println("12345");
                    String token = TokenUtil.getTokenGenerator(request.getHeader("User-Agent"),user);
                    jedis.setex(user.getUserCode(),3600,token);
                    Cookie cookie = new Cookie("token",token);
                    cookie.setMaxAge(3600);
                    response.addCookie(cookie);
                    return DtoUtil.returnSuccess("登录成功，前往登录页面");
                }else{
                    System.out.println("123456");
                    return DtoUtil.returnFail("登录失败，密码不对 ",ErrorCode.AUTH_AUTHENTICATION_FAILED);

                }
            }else{
                /**没有激活*/

                return DtoUtil.returnFail("登录失败，账号未激活 ",ErrorCode.AUTH_AUTHENTICATION_FAILED);

            }
        }


    }


/****token验证*****/
    @RequestMapping(value = "/checkToken")
    public Dto checkToken(HttpServletRequest request, HttpServletResponse response,@RequestBody CheckTokenUtil checkTokenUtil){
        System.out.println(checkTokenUtil.getUserCode());
        System.out.println("Cooken ===="+request.getHeader("Cookie"));
        System.out.println("redis====="+jedis.get(checkTokenUtil.getUserCode()));
        if(jedis.get(checkTokenUtil.getUserCode()) != null){
            /***表示Token存在，已经在登录*/
            String btoken=request.getHeader("Cookie").toString().trim().substring(6);
            String rtoken=jedis.get(checkTokenUtil.getUserCode());
            if(btoken.lastIndexOf(";")>-1){
                btoken=btoken.substring(0,btoken.lastIndexOf(";"));
            }
            if(btoken.equals(rtoken) ){
                System.out.println(btoken);
                InsuranceUser user = iInsuranceUserService.getByUserCode(checkTokenUtil.getUserCode());
                return DtoUtil.returnSuccess("token验证成功,操作正常",user);
            }else {
                return DtoUtil.returnFail("token验证失败,token错误", ErrorCode.AUTH_USER_ALREADY_EXISTS);
            }
        }else{
            /***/
            return DtoUtil.returnFail("token验证失败,token失效或过期", ErrorCode.AUTH_USER_ALREADY_EXISTS);
        }
    }
    /**发送验证码*/
    @RequestMapping(value = "/sendVerCode")
    public Dto sendVerCode(HttpServletRequest request, HttpServletResponse response, @RequestBody CheckTokenUtil checkTokenUtil){
        System.out.println("forgetPwVoUser=="+checkTokenUtil);
        InsuranceUser user= iInsuranceUserService.getByUserCode(checkTokenUtil.getUserCode());

        if(user != null) {
            //判断是手机号还是邮箱
            if (jedis.get(user.getUserCode()) != null && jedis.get(user.getUserCode()).length() != 4) {
                if (user.getUserCode().indexOf("@") != -1) {
                    System.out.println("这个是邮箱发送");
                    String smssk = EmailUtil.getEmailSend(user);
                    System.out.println(smssk);
                    jedis.setex(user.getUserCode(), 120, smssk);
                } else {
                    System.out.println("这个是短信发送");
                    String smssk = SmsUtil.getSmsSeng(user);
                    System.out.println(smssk);
                    jedis.setex(user.getUserCode(), 120, smssk);
                }
                return DtoUtil.returnSuccess("验证码发送成功");
            }else {
                return DtoUtil.returnFail("请不要在120秒内重复发送",ErrorCode.AUTH_USER_ALREADY_EXISTS);
            }
        }
        return  DtoUtil.returnFail("账号不存在",ErrorCode.AUTH_USER_ALREADY_EXISTS);

    }
    /****修改密码*****/
    @RequestMapping(value = "/forgetPw")
    public Dto forgetPw(HttpServletRequest request, HttpServletResponse response, @RequestBody ForgetPwVo forgetPwVoUser){
        System.out.println("forgetPwVoUser=="+forgetPwVoUser);

        String smssk =jedis.get(forgetPwVoUser.getUserCode());
        System.out.println("smssk"+smssk);
        if(smssk.equals(forgetPwVoUser.getCode())) {
            System.out.println("jinruxiugai");
            String userPw = MD5Util.getMd5(forgetPwVoUser.getUserPassword(), 32);
            iInsuranceUserService.forgetPw(forgetPwVoUser.getUserCode(), userPw);
            return DtoUtil.returnSuccess("修改成功");
        }
        return DtoUtil.returnFail("修改失败，验证码不正确",ErrorCode.AUTH_USER_ALREADY_EXISTS);
    }
    /**退出登录*/
    @RequestMapping(value = "/outToken")
    public Dto outToken(HttpServletRequest request, HttpServletResponse response,@RequestBody CheckTokenUtil checkTokenUtil){
        String userCode = checkTokenUtil.getUserCode();
        System.out.println("token退出方法:==>"+userCode);
        if(userCode!=null) {
            TokenUtil.delete(request, response, userCode);
            return DtoUtil.returnSuccess("退出登录成功,跳转登录页面");
        }else{
            return DtoUtil.returnFail("退出登录失败", ErrorCode.AUTH_USER_ALREADY_EXISTS);
        }

    }

}
