package com.util;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.po.InsuranceUser;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class SmsUtil {
    public static String getSmsSeng(InsuranceUser user) {
        //初始化sdk
        CCPRestSmsSDK ccpRestSmsSDK = new CCPRestSmsSDK();
        //初始化服务器端口和地址,网址是容联云首页找到的
        ccpRestSmsSDK.init("app.cloopen.com","8883");
        //设置账号和auth码值  账号也是在官网的用户界面找到的
        ccpRestSmsSDK.setAccount("8aaf0708842397dd01843647d1de050f","368e6ac363f642d1a0356d9cf33ddf6c");
        // 设置appid,在用户界面找到
        ccpRestSmsSDK.setAppId("8aaf0708842397dd01843647d2b50516");

        //生成验证码
        Date date = new Date();
        Long date1= date.getTime();
        String date2 = date1+"";
        String smssk = date2.substring(date2.length()-4,date2.length());

        //设置内容时常拿到返回状态码  to"电话号码" ,  templaeld"模板"   String[]{"验证码","时长"}
        HashMap<String , Object> result = ccpRestSmsSDK.sendTemplateSMS("13111260967","1",new String[]{smssk,"2"});
        //判断短信是否发送成功
        if("000000".equals(result.get("statusCode"))){
            System.out.println("短信发送成功");
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            };
            return smssk;
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
        return null;
    }
}
