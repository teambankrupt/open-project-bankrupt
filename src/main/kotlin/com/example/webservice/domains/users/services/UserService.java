package com.example.webservice.domains.users.services;

import com.example.webservice.domains.users.models.entities.User;
import com.example.webservice.exceptions.exists.UserAlreadyExistsException;
import com.example.webservice.exceptions.forbidden.ForbiddenException;
import com.example.webservice.exceptions.invalid.InvalidException;
import com.example.webservice.exceptions.invalid.UserInvalidException;
import com.example.webservice.exceptions.notfound.UserNotFoundException;
import com.example.webservice.exceptions.nullpointer.NullPasswordException;
import com.example.webservice.exceptions.unknown.UnknownException;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface UserService {
    User findByUsername(String username) throws UserNotFoundException;

    User findByPhoneNumber(String phoneNumber) throws UserNotFoundException, InvalidException;

    User findByEmail(String email) throws UserNotFoundException;

    User findByUsernameOrPhone(String usernameOrPhone) throws UserNotFoundException;

    Page<User> searchUser(String query, int page, int size);

    Page<User> findAll(int page);

    Page<User> findByRole(String role, int page);

    List<User> findByRole(String role);

    User findOne(Long id) throws UserNotFoundException;

    User save(User user) throws UserAlreadyExistsException, UserInvalidException, NullPasswordException;

    boolean exists(User user);

    User getAuthentication(String username, String password) throws UserNotFoundException, NullPasswordException;

    boolean requireAccountValidationByOTP(String phone, Date tokenValidUntil) throws InvalidException, UserAlreadyExistsException, ForbiddenException;

    void requireAccountValidationByEmail(String email, String validationUrl) throws UserNotFoundException;

    @Transactional
    User resetPassword(String username, String token, String password) throws NullPasswordException, UserAlreadyExistsException, UserInvalidException, ForbiddenException;

    Page<User> findUsersIn(List<Long> userIds, int page);

//    User changeRole(Long id, String role) throws UserNotFoundException;

    User changePassword(Long id, String currentPassword, String newPassword) throws NullPasswordException, UserNotFoundException, InvalidException, ForbiddenException;

    User setPassword(Long id, String newPassword) throws NullPasswordException, UserNotFoundException, InvalidException, ForbiddenException;

    void handlePasswordResetRequest(String username) throws UserNotFoundException, ForbiddenException, UnknownException;

    User setRoles(Long id, List<Long> roleIds) throws UserNotFoundException, UserAlreadyExistsException, NullPasswordException, UserInvalidException;
}
