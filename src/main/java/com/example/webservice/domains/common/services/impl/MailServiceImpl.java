package com.example.webservice.domains.common.services.impl;

import com.example.webservice.domains.common.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(@Qualifier("javaMailSenderBean") JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean sendEmail(String email, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    javaMailSender.send(mailMessage);
                }
            }).start();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return true;
    }
}
