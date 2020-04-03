package com.example.webservice.domains.users.services.beans;

import com.example.webservice.domains.users.models.entities.User;
import com.example.webservice.domains.users.services.UserService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * @author mir00r on 3/4/20
 * @project IntelliJ IDEA
 */
public class ConnectionSignUpImpl implements ConnectionSignUp {

    private UserService userService;

    public ConnectionSignUpImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(Connection<?> connection) {
        User userAccount = this.userService.createSocialLoginUser(connection);
        return userAccount.getUsername();
    }
}
