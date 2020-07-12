package com.example.app.domains.notifications.services;

import com.example.app.domains.notifications.models.entities.FirebaseUserToken;
import com.example.app.exceptions.invalid.InvalidException;
import com.example.app.exceptions.notfound.FirebaseTokenNotFoundException;
import com.example.app.exceptions.notfound.UserNotFoundException;

public interface FirebaseTokenService {
    FirebaseUserToken save(FirebaseUserToken token) throws InvalidException;

    FirebaseUserToken get(Long userId) throws FirebaseTokenNotFoundException;

    FirebaseUserToken save(Long userId, String token) throws InvalidException, UserNotFoundException;
}
