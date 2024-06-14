package org.oauth2.shopapp.service;

import org.oauth2.shopapp.constant.EmailManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail( String to, String subject, String msg) {
        //creating message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(EmailManagement.SENDER);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        //sending message
        mailSender.send(message);
    }
}
