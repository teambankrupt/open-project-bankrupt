package com.example.webservice.domains.firebase.controllers;

import com.example.webservice.config.security.SecurityContext;
import com.example.webservice.domains.firebase.services.FirebaseTokenService;
import com.example.webservice.domains.users.models.entities.User;
import com.example.webservice.exceptions.invalid.InvalidException;
import com.example.webservice.exceptions.notfound.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@Controller
@RequestMapping("/api/v1/firebase")
public class FirebaseController {
    private final FirebaseTokenService firebaseTokenService;

    @Autowired
    public FirebaseController(FirebaseTokenService firebaseTokenService) {
        this.firebaseTokenService = firebaseTokenService;
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    private void saveToken(@RequestParam("token") String token) throws UserNotFoundException, InvalidException {
        this.firebaseTokenService.save(Objects.requireNonNull(SecurityContext.getCurrentUser()).getId(), token);
    }

}
