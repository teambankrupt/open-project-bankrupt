package com.example.webservice.domains.common.controllers;


import com.example.webservice.domains.common.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {
    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/api/v1/test-email")
    private ResponseEntity testEmail(@RequestParam("email") String email,
                                     @RequestParam(value = "subject", defaultValue = "Test Email") String subject,
                                     @RequestParam(value = "message", defaultValue = "Hi,\n\nEmail is working!!") String message) {
        this.mailService.sendEmail(email, subject, message);
        return ResponseEntity.ok("Email Sent!!");
    }
}
