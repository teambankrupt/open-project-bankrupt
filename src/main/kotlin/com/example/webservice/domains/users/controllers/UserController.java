package com.example.webservice.domains.users.controllers;

import com.example.webservice.config.security.TokenService;
import com.example.webservice.domains.users.models.entities.User;
import com.example.webservice.exceptions.exists.UserAlreadyExistsException;
import com.example.webservice.exceptions.forbidden.ForbiddenException;
import com.example.webservice.exceptions.invalid.InvalidException;
import com.example.webservice.exceptions.invalid.UserInvalidException;
import com.example.webservice.exceptions.notfound.UserNotFoundException;
import com.example.webservice.exceptions.nullpointer.NullPasswordException;
import com.example.webservice.domains.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("")
    public ResponseEntity all(@RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "role", required = false) String role) {
        Page<User> userPage;
        if (role == null)
            userPage = this.userService.findAll(page);
        else
            userPage = this.userService.findByRole(role, page);

        return ResponseEntity.ok(userPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable("id") Long userId) throws UserNotFoundException {
        User user = this.userService.findOne(userId);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/search")
    private ResponseEntity searchUser(@RequestParam("q") String query,
                                      @RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size) throws ForbiddenException, UserNotFoundException {
        Page<User> userPage = this.userService.searchUser(query, page, size);
        return ResponseEntity.ok(userPage);
    }


    @GetMapping("/byRole")
    public ResponseEntity byRole(@RequestParam(value = "role") String role,
                                 @RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(this.userService.findByRole(role));
    }


    @PostMapping("/{id}/access/toggle")
    public ResponseEntity disableUser(@PathVariable("id") Long id,
                                      @RequestParam("enabled") boolean enabled) throws UserNotFoundException, UserAlreadyExistsException, NullPasswordException, UserInvalidException {
        User user = this.userService.findOne(id);
        user.setEnabled(enabled);
        user = this.userService.save(user);
        this.tokenService.revokeAuthentication(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/changeRole")
    private ResponseEntity changeRole(@PathVariable("id") Long id,
                                      @RequestParam("roles") String[] roles) throws UserNotFoundException, UserInvalidException, UserAlreadyExistsException, NullPasswordException {
        User user = this.userService.setRoles(id, roles);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/access/change")
    public ResponseEntity changeAccess(@PathVariable("id") Long id,
                                       @RequestParam("role") String role) throws UserNotFoundException {
        User user = this.userService.changeRole(id, role);
        this.tokenService.revokeAuthentication(user);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/changePassword")
    public ResponseEntity changePassword(@PathVariable("id") Long userId,
                                         @RequestParam("newPassword") String newPassword) throws UserNotFoundException, NullPasswordException, ForbiddenException, InvalidException {
        this.userService.setPassword(userId, newPassword);
        return ResponseEntity.ok().build();
    }


}
