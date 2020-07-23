package com.example.auth.listeners;

import com.example.auth.services.LoginAttemptService;
import com.example.auth.utils.NetworkUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureEventListener
        implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final LoginAttemptService loginAttemptService;

    @Autowired
    public AuthenticationFailureEventListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    public void onApplicationEvent(@NotNull AuthenticationFailureBadCredentialsEvent e) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                .getRequest();
        loginAttemptService.loginFailed(NetworkUtil.getClientIP());
    }
}
