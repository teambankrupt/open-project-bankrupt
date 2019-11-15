package com.example.webservice.domains.common.services;

public interface MailService {
    boolean sendEmail(String email, String subject, String message);
}
