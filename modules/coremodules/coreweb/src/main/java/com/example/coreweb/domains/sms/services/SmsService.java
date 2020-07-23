package com.example.coreweb.domains.sms.services;

public interface SmsService {
    boolean sendSms(String phoneNumber, String message);
}
