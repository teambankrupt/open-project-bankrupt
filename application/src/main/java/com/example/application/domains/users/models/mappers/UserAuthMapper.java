//package com.example.application.domains.users.models.mappers;
//
//import com.example.auth.entities.User;
//import com.example.auth.entities.UserAuth;
//
//public class UserAuthMapper {
//
//    public static UserAuth getAuth(User user) {
//        UserAuth auth = new UserAuth();
//        auth.setId(user.getId());
//        auth.setName(user.getName());
//        auth.setUsername(user.getUsername());
//        auth.setPassword(user.getPassword());
//        auth.setPhone(user.getPhone());
//        auth.setEmail(user.getEmail());
//        auth.setEnabled(user.isEnabled());
//        auth.setAccountNonExpired(user.isAccountNonExpired());
//        auth.setAccountNonLocked(user.isAccountNonLocked());
//        auth.setCredentialsNonExpired(user.isCredentialsNonExpired());
//
//        return auth;
//    }
//
//    public static User getUser(UserAuth auth) {
//        User user = new User();
//        user.setId(auth.getId());
//        user.setName(auth.getName());
//        user.setUsername(auth.getUsername());
//        user.setPassword(auth.getPassword());
//        user.setPhone(auth.getPhone());
//        user.setEmail(auth.getEmail());
//        user.setEnabled(auth.isEnabled());
//        user.setAccountNonExpired(auth.isAccountNonExpired());
//        user.setAccountNonLocked(auth.isAccountNonLocked());
//        user.setCredentialsNonExpired(auth.isCredentialsNonExpired());
//
//        return user;
//    }
//
//}
