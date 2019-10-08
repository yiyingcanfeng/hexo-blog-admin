package com.movefeng.hexoblogadmin.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author z
 */
@Slf4j
public class SendMail {

    public static void sendSimpleMail(JavaMailSender javaMailSender,String from,String to,String subject,String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public static void sendHtmlMail(JavaMailSender mailSender,String from,String to,String subject,String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error(String.format("邮件发送失败:Exception:%s,%s", e, e.getMessage()));
            e.printStackTrace();
        }
    }

}
