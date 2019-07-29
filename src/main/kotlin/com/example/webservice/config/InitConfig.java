package com.example.webservice.config;

import com.example.webservice.entities.User;
import com.example.webservice.exceptions.exists.UserAlreadyExistsException;
import com.example.webservice.exceptions.invalid.UserInvalidException;
import com.example.webservice.exceptions.nullpointer.NullPasswordException;
import com.example.webservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitConfig {

    private final UserService userService;
    @Value("${admin.phone1}")
    private String adminPhone1;
    @Value("${admin.password1}")
    private String adminPass1;

    @Autowired
    public InitConfig(UserService userService) {
        this.userService = userService;
    }

    public void onBootUp() throws UserAlreadyExistsException, NullPasswordException, UserInvalidException {
        User user = new User();
        user.setName("Admin");
        user.setUsername(adminPhone1);
        user.setPhoneNumber(adminPhone1);
        user.setPassword(adminPass1);
        this.userService.save(user);
    }

}
