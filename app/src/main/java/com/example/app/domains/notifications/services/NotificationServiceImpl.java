package com.example.app.domains.notifications.services;

import com.example.app.domains.notifications.models.dto.PushNotification;
import com.example.app.domains.notifications.models.entities.FirebaseUserToken;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.unknown.UnknownException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@PropertySource("classpath:security.properties")
public class NotificationServiceImpl implements NotificationService {

    @Value("${auth.fcm.serverkey}")
    private String fcmServerKey;

    private final FirebaseTokenService firebaseTokenService;

    @Autowired
    public NotificationServiceImpl(FirebaseTokenService firebaseTokenService) {
        this.firebaseTokenService = firebaseTokenService;
    }

    @Override
    public void sendNotification(Long userId, PushNotification notification) throws InvalidException, NotFoundException, JsonProcessingException, UnknownException {
        if (userId == null || notification == null)
            throw new InvalidException("Could not send notification, some item is null");
        FirebaseUserToken token = this.firebaseTokenService.get(userId);
        if (token == null) return;
        notification.setTo(token.getUserToken());
        String notiString = new ObjectMapper().writeValueAsString(notification);

        try {
            this.postData(notiString);
        } catch (IOException e) {
            throw new UnknownException(e.getMessage());
        }

    }

    @Override
    public void sendNotification(PushNotification notification) throws InvalidException, JsonProcessingException, UnknownException {
        if (notification == null) throw new InvalidException("Could not send notification, some item is null");
        String notiString = new ObjectMapper().writeValueAsString(notification);
        System.out.println(notiString);
        try {
            this.postData(notiString);
        } catch (IOException e) {
            throw new UnknownException(e.getMessage());
        }
    }

    private void postData(String notiString) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://fcm.googleapis.com/fcm/send");

        StringEntity entity = new StringEntity(notiString, "UTF-8");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", "key=" + this.fcmServerKey);
        CloseableHttpResponse response = client.execute(httpPost);
        client.close();
        response.close();
//        HttpEntity e = response.getEntity();
//        InputStream is = e.getContent();
    }



}
