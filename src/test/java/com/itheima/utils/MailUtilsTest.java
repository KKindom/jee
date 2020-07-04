package com.itheima.utils;

import com.kkindom_blog.utils.MailUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailUtilsTest {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailUtils mailUtils;
    @Test
    public void contextLoads() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setSubject("这是一封测试邮件");
//        message.setFrom("1184045779@qq.com");
//        message.setTo("1184045779@qq.com");
////        message.setCc("1184045779@qq.com");
////        message.setBcc("1184045779@qq.com");
////        message.setSentDate(new Date());
//        message.setText("这是测试邮件的正文");
//        javaMailSender.send(message);
        mailUtils.sendSimpleEmail("1184045779@qq.com","测试","测试");
    }
}