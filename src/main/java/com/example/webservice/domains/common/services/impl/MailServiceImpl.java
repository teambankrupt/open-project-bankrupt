package com.example.webservice.domains.common.services.impl;

import com.example.webservice.domains.common.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

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
            new Thread(() -> javaMailSender.send(mailMessage)).start();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return true;
    }

    @Override
    public void sendEmail(String to, String from, String sub, String msgBody, List<File> attachments) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            if (from != null)
                helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(sub);
            helper.setText(msgBody);
            if (attachments != null)
                for (File a : attachments)
                    helper.addAttachment(a.getName(), new FileSystemResource(a));

            new Thread(() -> javaMailSender.send(message)).start();
        } catch (MessagingException e) {
            System.out.println(e.toString());
        }
    }

}
