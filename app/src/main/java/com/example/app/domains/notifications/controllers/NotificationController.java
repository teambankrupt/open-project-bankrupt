package com.example.app.domains.notifications.controllers;

import com.example.auth.config.security.SecurityContext;
import com.example.app.domains.notifications.services.FirebaseTokenService;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.UserNotFoundException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@Controller
@RequestMapping("/api/v1/notifications")
@Api(tags = "Notifications", description = "Fetch notifications, register firebase tokens etc")
public class NotificationController {
    private final FirebaseTokenService firebaseTokenService;

    @Autowired
    public NotificationController(FirebaseTokenService firebaseTokenService) {
        this.firebaseTokenService = firebaseTokenService;
    }

    @PostMapping("/firebase/token")
    @ResponseStatus(HttpStatus.OK)
    private void saveToken(@RequestParam("token") String token) throws UserNotFoundException, InvalidException {
        this.firebaseTokenService.save(Objects.requireNonNull(SecurityContext.getCurrentUser()).getId(), token);
    }

}
