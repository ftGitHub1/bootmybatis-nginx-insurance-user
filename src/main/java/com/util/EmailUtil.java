package com.util;

import com.po.InsuranceUser;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {
    public static String account = "ft1301622500@163.com";			//替换为发件人账号
    public static String password = "ANCSZRMXPXGTKLOP";					//替换为发件人账号密码 授权码

    public static String receiveMailAccount = "1301622500@qq.com";	//替换为收件人账号
    public static String getEmailSend(InsuranceUser user) {
        // 1. 使用Properties对象封装连接所需的信息
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议 来自于网易邮箱
        props.setProperty("mail.smtp.host", "smtp.163.com");   	// 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        //生成验证码
        Date date = new Date();
        Long date1= date.getTime();
        String date2 = date1+"";
        String smssk = date2.substring(date2.length()-4,date2.length());
        try {
            // 2. 获取Session对象
            Session session = Session.getDefaultInstance(props);
            // 3. 封装Message对象  account:发件箱，
            MimeMessage message = createMimeMessage(session, account, user.getUserCode(),user,smssk);
            // 4. 使用Transport发送邮件
            Transport transport = session.getTransport();
            transport.connect(account, password);
            transport.sendMessage(message, message.getAllRecipients());
            // 5. 关闭连接
            transport.close();
            System.out.println("发送成功！");
            return smssk;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //邮件设置
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, InsuranceUser user,String smssk)
            throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "福寿保险", "UTF-8"));
        // 3. To: 收件人
        message.setRecipient(MimeMessage.RecipientType.TO,
                new InternetAddress(receiveMail, user.getUserName()+"用户", "UTF-8"));
        // 4. Subject: 邮件主题
        message.setSubject("邮件测试", "UTF-8");
        // 5. Content: 邮件正文
        message.setContent( user.getUserName()+"用户你好,你的激活验证码是"+smssk+"请在二分钟内回复", "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }
}
