package com.saras.archetype.utils;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * description:
 *
 * @author saras_xu@163.com
 * @date 2018-03-26 14:32 创建
 */
public class MailUtils {

    private static final String HOST = "smtp.exmail.qq.com";
    private static final Integer PORT = 25;
    private static final String USERNAME = "meeting@by-saas.com";
    private static final String PASSWORD = "Qazwsx12";
    private static final String EMAIL_FORM = "meeting@by-saas.com";
    private static JavaMailSenderImpl mailSender = createMailSender();

    /**
     * 邮件发送器
     *
     * @return 配置好的工具
     */
    private static JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST);
        sender.setPort(PORT);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);
        sender.setDefaultEncoding("UTF-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "25000");
        p.setProperty("mail.smtp.auth", "true");
        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 发送邮件
     *
     * @param emailTo 接受人
     * @param subject 主题
     * @param text    发送内容
     * @throws MessagingException           异常
     * @throws UnsupportedEncodingException 异常
     */
    public static void sendMail(String emailTo, String subject, String text) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(EMAIL_FORM, "E-MEETING");
        messageHelper.setTo(emailTo);
        messageHelper.setSubject(subject);
        messageHelper.setText(text, true);
        mailSender.send(mimeMessage);
    }

    public static void main(String[] args) throws UnsupportedEncodingException, MessagingException {
        sendMail("345991260@qq.com", "测试邮件", "Saras邀请你于XXXX和XXX参加会议");
    }
}
