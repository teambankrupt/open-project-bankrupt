package com.example.webservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.webservice.entities.firebase.PushNotification;
import com.example.webservice.exceptions.invalid.InvalidException;
import com.example.webservice.exceptions.notfound.NotFoundException;
import com.example.webservice.exceptions.unknown.UnknownException;

public interface NotificationService {
    void sendNotification(Long userId, PushNotification notification) throws InvalidException, NotFoundException, JsonProcessingException, UnknownException;

    void sendNotification(PushNotification notification) throws InvalidException, JsonProcessingException, UnknownException;

}
