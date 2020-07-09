package com.example.coreweb.domains.mail.controllers;


import com.example.coreweb.domains.mail.services.MailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Email Configs/Services", description = "Email services like testing if email works etc.")
public class MailController {
    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/api/v1/test-email")
    private ResponseEntity<String> testEmail(@RequestParam("email") String email,
                                             @RequestParam(value = "subject", defaultValue = "Test Email") String subject,
                                             @RequestParam(value = "message", defaultValue = "Hi,\n\nEmail is working!!") String message) {
        this.mailService.sendEmail(email, subject, message);
        return ResponseEntity.ok("Email Sent!!");
    }
}
