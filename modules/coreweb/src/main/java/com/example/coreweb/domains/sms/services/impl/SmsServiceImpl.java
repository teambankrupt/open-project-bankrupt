package com.example.coreweb.domains.sms.services.impl;

import com.example.common.utils.NetworkUtil;
import com.example.coreweb.domains.sms.services.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@PropertySource("classpath:sms.properties")
public class SmsServiceImpl implements SmsService {

    @Value("${mimsms.apiKey}")
    String apiKey;
    @Value("${mimsms.senderId}")
    String senderId;

    @Override
    public boolean sendSms(String phoneNumber, String message) {
        String phone = phoneNumber.startsWith("88") ? phoneNumber : "88" + phoneNumber;
        String url = "http://brandsms.mimsms.com/smsapi?api_key=" + this.apiKey + "&type=text&contacts=" + phone +
                "&senderid=" + this.senderId + "&msg=" + message;
        try {
            NetworkUtil.postData(url, null, null);
            return true;
        } catch (IOException e) {
            System.out.println("Could not send SMS. " + e.getMessage());
            return false;
        }
    }
}
