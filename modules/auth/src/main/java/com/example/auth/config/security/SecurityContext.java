package com.example.auth.config.security;

import com.example.auth.domains.models.entities.UserAuth;
import com.example.auth.services.AuthService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public final class SecurityContext {

    private static AuthService authService;
    private static UserAuth loggedInUser;

    public SecurityContext(AuthService authService) {
        SecurityContext.authService = authService;
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
        return authService.findAuthUser(username);
    }

    public static boolean isAuthenticated() {
        return getCurrentUser() != null;
    }
}
