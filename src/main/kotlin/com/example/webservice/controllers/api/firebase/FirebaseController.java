package com.example.webservice.controllers.api.firebase;

import com.example.webservice.entities.User;
import com.example.webservice.entities.annotations.CurrentUser;
import com.example.webservice.exceptions.invalid.InvalidException;
import com.example.webservice.exceptions.notfound.UserNotFoundException;
import com.example.webservice.services.FirebaseTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    private void saveToken(@CurrentUser User user,
                           @RequestParam("token") String token) throws UserNotFoundException, InvalidException {
        this.firebaseTokenService.save(user.getId(), token);
    }

}
