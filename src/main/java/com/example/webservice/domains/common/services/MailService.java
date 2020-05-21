package com.example.webservice.domains.common.services;

import java.io.File;
import java.util.List;

public interface MailService {
    boolean sendEmail(String email, String subject, String message);

    void sendEmail(String email, String from, String subject, String message, List<File> attachments);

}
