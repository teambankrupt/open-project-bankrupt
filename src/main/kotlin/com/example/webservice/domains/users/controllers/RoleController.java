package com.example.webservice.domains.users.controllers;


import com.example.webservice.domains.users.models.entities.Role;
import com.example.webservice.domains.users.models.entities.User;
import com.example.webservice.domains.users.models.annotations.CurrentUser;
import com.example.webservice.exceptions.forbidden.ForbiddenException;
import com.example.webservice.exceptions.notfound.UserNotFoundException;
import com.example.webservice.domains.users.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    private ResponseEntity getMyRoles(@CurrentUser User currentUser) throws ForbiddenException, UserNotFoundException {
        List<Role> roles = this.roleService.findByUser(currentUser.getId());
        return ResponseEntity.ok(roles);
    }

}
