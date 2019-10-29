package com.example.webservice.domains.common.services;

public interface MailService {
    void sendEmail(String email, String subject, String message);
}
