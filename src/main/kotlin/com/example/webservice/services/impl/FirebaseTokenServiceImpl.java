package com.example.webservice.services.impl;

import com.example.webservice.entities.User;
import com.example.webservice.entities.firebase.FirebaseUserToken;
import com.example.webservice.services.FirebaseTokenService;
import com.example.webservice.services.UserService;
import com.example.webservice.exceptions.invalid.InvalidException;
import com.example.webservice.exceptions.notfound.FirebaseTokenNotFoundException;
import com.example.webservice.exceptions.notfound.UserNotFoundException;
import com.example.webservice.repositories.FirebaseTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseTokenServiceImpl implements FirebaseTokenService {
    private final FirebaseTokenRepository firebaseTokenRepo;
    private final UserService userService;

    @Autowired
    public FirebaseTokenServiceImpl(FirebaseTokenRepository firebaseTokenRepo, UserService userService) {
        this.firebaseTokenRepo = firebaseTokenRepo;
        this.userService = userService;
    }

    @Override
    public FirebaseUserToken save(FirebaseUserToken token) throws InvalidException {
        if (token == null) throw new InvalidException("Token can not be null!");
        return this.firebaseTokenRepo.save(token);
    }

    @Override
    public FirebaseUserToken get(Long userId) throws FirebaseTokenNotFoundException {
        FirebaseUserToken token = this.firebaseTokenRepo.findByUserId(userId);
        if (token == null) return null;
        return token;
    }

    @Override
    public FirebaseUserToken save(Long userId, String token) throws InvalidException, UserNotFoundException {
        if (userId == null || token == null) throw new InvalidException("userId or token can not be null");
        User user = this.userService.findOne(userId);
        FirebaseUserToken firebaseUserToken = this.firebaseTokenRepo.findByUserId(userId);
        if (firebaseUserToken != null)
            firebaseUserToken.setUserToken(token);
        else
            firebaseUserToken = new FirebaseUserToken(user, token);
        return this.save(firebaseUserToken);
    }
}
