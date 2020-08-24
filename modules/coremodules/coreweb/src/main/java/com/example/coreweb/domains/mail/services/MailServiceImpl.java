package com.example.coreweb.domains.mail.services;

import com.example.coreweb.domains.mail.models.entities.EmailLog;
import com.example.coreweb.domains.mail.repositories.EmailLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final EmailLogRepository emailLogRepository;

    @Autowired
    public MailServiceImpl(@Qualifier("javaMailSenderBean") JavaMailSender javaMailSender, EmailLogRepository emailLogRepository) {
        this.javaMailSender = javaMailSender;
        this.emailLogRepository = emailLogRepository;
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
            return false;
        }
        this.saveLog(email, null, null, null, subject, message, 0);
        return true;
    }

    @Override
    public boolean sendEmail(String to, String from, String sub, String msgBody, List<File> attachments) {
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
            return false;
        }
        this.saveLog(to, null, null, from, sub, msgBody, attachments == null ? 0 : attachments.size());
        return true;
    }

    public boolean sendEmail(String email, String[] cc, String[] bcc, String subject, String message) {
        SimpleMailMessage mailMessage = buildMessage(email, cc, bcc, subject, message);
        try {
            new Thread(() -> javaMailSender.send(mailMessage)).start();
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
        this.saveLog(email, StringUtils.join(cc, ","), StringUtils.join(bcc, ","), null, subject, message, 0);
        return true;
    }

    private SimpleMailMessage buildMessage(String email, String[] cc, String[] bcc, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        if (cc != null && cc.length > 0)
            mailMessage.setCc(cc);
        if (bcc != null && bcc.length > 0)
            mailMessage.setBcc(bcc);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        return mailMessage;
    }

    private void saveLog(String to, String cc, String bcc, String from, String subject, String msg, int noOfAttachments) {
        EmailLog log = new EmailLog(from == null ? "SystemMail" : from, to, cc, bcc, subject, msg, noOfAttachments);
        this.emailLogRepository.save(log);
    }


}
