package com.example.webservice.config.security;

import com.example.webservice.domains.users.models.entities.User;
import com.example.webservice.domains.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SecurityContext {

    private static UserRepository userRepository;
    private static User loggedInUser;

    public SecurityContext(UserRepository userRepository) {
        SecurityContext.userRepository = userRepository;
    }

//    @Autowired
//    private UserRepository uRepo;
//
//    @PostConstruct
//    public void init() {
//        SecurityContext.userRepository = uRepo;
//    }

    public static void updateAuthentication(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getPrincipal() instanceof String) {
                if (loggedInUser == null || !authentication.getPrincipal().equals(loggedInUser.getUsername()))
                    loggedInUser = SecurityContext.userRepository.findByUsername((String) authentication.getPrincipal()).orElse(null);
                return loggedInUser;
            }
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    public static boolean isAuthenticated() {
        return getCurrentUser() != null;
    }
}
