package com.springboot.cs.common.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class JavaMailUtil {

    //收件人地址
    //654969082@qq.com
    //912883761@qq.com
//    public static String recipientAddress = "LILI120630@HOTMAIL.COM";
    public static String recipientAddress = "1357817353@qq.com";

    //发件人地址
    public static String senderAddress = "administer@imeetlink.com";

    //发件人账户名
    public static String senderAccount = "administer@imeetlink.com";

    //发件人账户密码
    public static String senderPassword = "JBpXRuA5bJZgQpar";

    //smtp服务器
    private static String host = "smtp.exmail.qq.com";

    private static String theme = "叶宇维的测试";

    private static String content = "iMeetLink:您正在申请注册，验证码是：1234";

    /**
     * 发送邮件
     * @param recipientAddress
     * @param senderAddress
     */
    public static void sendMail(String host,String senderAddress,String senderAccount,String senderPassword,String recipientAddress,String theme,String content) throws Exception{
        System.out.println("开始发送");
        //配置参数
        Session session = initSession(host);
        session.setDebug(true);
        //创建邮件
        Message msg = initMessage(session,senderAddress,recipientAddress,theme,content);

        //根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();

        //设置发件人的账户名和密码
        transport.connect(senderAccount, senderPassword);

        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg,msg.getAllRecipients());

        //5、关闭邮件连接
        transport.close();
        System.out.println("发送完成");
    }


    /**
     * 配置服务器参数配置
     * @return
     */
    private static Session initSession(String host) throws Exception{

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        // 设置信任所有的主机
        sf.setTrustAllHosts( true );

        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", host);

        props.setProperty( "mail.smtp.ssl.enable", "true" );

        props.put( "mail.smtp.ssl.socketFactory",sf );

//        // 创建验证器
//        Authenticator auth = new Authenticator() {
//            public PasswordAuthentication getPasswordAuthentication() {
//                // 密码验证，第一个参数：邮箱名不包括@163.com，第二个参数：授权码
//                return new PasswordAuthentication( "邮箱名不包括@163.com", "授权码" );
//
//            }
//        };
//        Session session = Session.getInstance( props, auth );
//
        //返回环境信息的 Session 对象
        return Session.getInstance(props);


    }

    /**
     * 创建邮件
     * @param session
     * @return
     */
    private static Message initMessage(Session session,String senderAddress,String recipientAddress,String theme,String content) throws Exception{

        MimeMessage msg = new MimeMessage(session);

        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));

        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */

        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));

        //设置邮件主题
        msg.setSubject(theme,"UTF-8");

        //设置邮件正文
        msg.setContent(content, "text/html;charset=UTF-8");

        msg.getHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");
//        msg.setText(content);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;

    }

    public static void main(String[] args) throws Exception {

        sendMail(host,senderAddress,senderAccount,senderPassword,recipientAddress,theme,content);

    }

}
