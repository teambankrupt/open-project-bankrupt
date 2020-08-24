package com.example.auth.config.security;

import com.example.auth.entities.User;
import com.example.auth.entities.UserAuth;
import com.example.auth.repositories.UserRepo;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public final class SecurityContext {

    private static UserRepo userRepo;
    private static UserAuth loggedInUser;

    public SecurityContext(UserRepo userRepo) {
        SecurityContext.userRepo = userRepo;
    }

//    @Autowired
//    private UserRepository uRepo;
//
//    @PostConstruct
//    public void init() {
//        SecurityContext.userRepository = uRepo;
//    }

    public static void updateAuthentication(UserAuth user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getPrincipal() instanceof String)
                return (String) authentication.getPrincipal();
            else
                return ((UserAuth) authentication.getPrincipal()).getUsername();
        }
        return null;
    }

    public static UserAuth getCurrentUser() {
        String username = getLoggedInUsername();
        User user = userRepo.find(username).orElse(null);
        return user == null ? null : new UserAuth(user);
    }

    public static boolean isAuthenticated() {
        return getCurrentUser() != null;
    }
}
