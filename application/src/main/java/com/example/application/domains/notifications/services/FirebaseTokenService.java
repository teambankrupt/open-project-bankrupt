package com.example.application.domains.notifications.services;

import com.example.application.domains.notifications.models.entities.FirebaseUserToken;
import com.example.application.exceptions.invalid.InvalidException;
import com.example.application.exceptions.notfound.FirebaseTokenNotFoundException;
import com.example.application.exceptions.notfound.UserNotFoundException;

public interface FirebaseTokenService {
    FirebaseUserToken save(FirebaseUserToken token) throws InvalidException;

    FirebaseUserToken get(Long userId) throws FirebaseTokenNotFoundException;

    FirebaseUserToken save(Long userId, String token) throws InvalidException, UserNotFoundException;
}
