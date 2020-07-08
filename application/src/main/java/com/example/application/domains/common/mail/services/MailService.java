package com.example.application.domains.common.mail.services;

import java.io.File;
import java.util.List;

public interface MailService {
    boolean sendEmail(String email, String subject, String message);

    boolean sendEmail(String email, String from, String subject, String message, List<File> attachments);

}
