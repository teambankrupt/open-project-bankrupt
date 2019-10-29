package com.example.webservice.domains.firebase.services;

import com.example.webservice.domains.firebase.models.entities.FirebaseUserToken;
import com.example.webservice.exceptions.invalid.InvalidException;
import com.example.webservice.exceptions.notfound.FirebaseTokenNotFoundException;
import com.example.webservice.exceptions.notfound.UserNotFoundException;

public interface FirebaseTokenService {
    FirebaseUserToken save(FirebaseUserToken token) throws InvalidException;

    FirebaseUserToken get(Long userId) throws FirebaseTokenNotFoundException;

    FirebaseUserToken save(Long userId, String token) throws InvalidException, UserNotFoundException;
}
