package com.example.webservice.services;

public interface MailService {
    void sendEmail(String email, String subject, String message);
}
