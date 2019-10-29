package com.example.webservice.domains.common.services;

public interface SmsService {
    boolean sendSms(String phoneNumber, String message);
}
