package com.example.application.domains.notifications.services;

import com.example.application.domains.notifications.models.dto.PushNotification;
import com.example.application.exceptions.invalid.InvalidException;
import com.example.application.exceptions.notfound.NotFoundException;
import com.example.application.exceptions.unknown.UnknownException;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface NotificationService {
    void sendNotification(Long userId, PushNotification notification) throws InvalidException, NotFoundException, JsonProcessingException, UnknownException;

    void sendNotification(PushNotification notification) throws InvalidException, JsonProcessingException, UnknownException;

}
