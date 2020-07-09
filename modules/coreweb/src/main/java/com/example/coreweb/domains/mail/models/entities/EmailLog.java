package com.example.coreweb.domains.mail.models.entities;


import com.example.coreweb.domains.base.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "email_logs")
public class EmailLog extends BaseEntity {

    @Column(name = "mail_from", nullable = false)
    private String from;

    @Column(name = "mail_to", nullable = false)
    private String to;

    @Column(name = "mail_cc", columnDefinition = "VARCHAR(511)")
    private String cc;

    @Column(name = "mail_bcc", columnDefinition = "VARCHAR(511)")
    private String bcc;

    @Column(name = "subject", columnDefinition = "VARCHAR(511)")
    private String subject;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "no_of_attachments")
    private int noOfAttachments;

    public EmailLog() {
    }

    public EmailLog(String from, String to, String cc, String bcc, String subject, String message, int noOfAttachments) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.message = message;
        this.noOfAttachments = noOfAttachments;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNoOfAttachments() {
        return noOfAttachments;
    }

    public void setNoOfAttachments(int noOfAttachments) {
        this.noOfAttachments = noOfAttachments;
    }
}