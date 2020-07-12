package com.example.app.domains.notifications.services;

import com.example.app.domains.notifications.models.dto.PushNotification;
import com.example.app.exceptions.invalid.InvalidException;
import com.example.app.exceptions.notfound.NotFoundException;
import com.example.app.exceptions.unknown.UnknownException;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface NotificationService {
    void sendNotification(Long userId, PushNotification notification) throws InvalidException, NotFoundException, JsonProcessingException, UnknownException;

    void sendNotification(PushNotification notification) throws InvalidException, JsonProcessingException, UnknownException;

}
