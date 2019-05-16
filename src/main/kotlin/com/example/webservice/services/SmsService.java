package com.example.webservice.services;

public interface SmsService {
    boolean sendSms(String phoneNumber, String message);
}
