package com.example.auth.services;

import com.example.auth.entities.UserAuth;
import com.example.auth.utils.NetworkUtil;
import com.example.common.exceptions.notfound.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthService authService;
    private final LoginAttemptService loginAttemptService;

    @Autowired
    public CustomUserDetailsService(AuthService authService, LoginAttemptService loginAttemptService) {
        this.authService = authService;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // flood control
        String ip = NetworkUtil.getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }
        // end flood control
        UserAuth auth;
        try {
            auth = this.authService.findAuthUser(username);
            if (auth == null) throw new UserNotFoundException("User doesn't exist!");
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("User doesn't exist!");
        }
        return auth;
    }


}
