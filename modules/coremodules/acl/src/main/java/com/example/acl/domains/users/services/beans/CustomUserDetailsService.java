//package com.example.application.domains.users.services.beans;
//
//import com.example.common.utils.NetworkUtil;
//import com.example.auth.entities.UserAuth;
//import com.example.auth.entities.User;
//import com.example.application.domains.users.services.UserService;
//import com.example.common.exceptions.notfound.UserNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserService userService;
//    private final LoginAttemptService loginAttemptService;
//
//    @Autowired
//    public CustomUserDetailsService(UserService userService, LoginAttemptService loginAttemptService) {
//        this.userService = userService;
//        this.loginAttemptService = loginAttemptService;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // flood control
//        String ip = NetworkUtil.getClientIP();
//        if (loginAttemptService.isBlocked(ip)) {
//            throw new RuntimeException("blocked");
//        }
//        // end flood control
//        User user;
//        try {
//            user = this.userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User doesn't exist!"));
//        } catch (UserNotFoundException e) {
//            throw new UsernameNotFoundException("User doesn't exist!");
//        }
//        if (user == null) throw new UsernameNotFoundException("User doesn't exist!");
//        return new UserAuth(user);
//    }
//
//
//}
