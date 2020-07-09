package com.example.application.config.security;

import com.example.application.config.security.oauth.AuthAdapter;
import com.example.application.domains.users.models.entities.User;
import com.example.application.domains.users.repositories.UserRepository;
import com.example.auth.domains.models.entities.UserAuth;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class SecurityContext {

    private static UserRepository userRepository;
    private static UserAuth loggedInUser;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getPrincipal() instanceof String) {
                if (loggedInUser == null || !authentication.getPrincipal().equals(loggedInUser.getUsername())) {
                    Optional<User> user = SecurityContext.userRepository.findByUsername((String) authentication.getPrincipal());
                    if (!user.isPresent()) return null;
                    loggedInUser = AuthAdapter.getAuth(user.get());
                }
                return loggedInUser;
            }
            return (UserAuth) authentication.getPrincipal();
        }
        return null;
    }

    public static boolean isAuthenticated() {
        return getCurrentUser() != null;
    }
}
