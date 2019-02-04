package com.example.webservice.services.impl;

import com.example.webservice.entities.Role;
import com.example.webservice.repositories.RoleRepository;
import com.example.webservice.repositories.UserRepository;
import com.example.webservice.services.RoleService;
import com.example.webservice.entities.User;
import com.example.webservice.exceptions.forbidden.ForbiddenException;
import com.example.webservice.exceptions.notfound.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private UserRepository userRepo;
    private final RoleRepository roleRepo;

    @Autowired
    public RoleServiceImpl(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public Role findRole(Role.ERole eRole) {
        Role role = this.findByRole(eRole.toString());
        if (role==null) {
            role = new Role(eRole);
            role = this.save(role);
        }
        return role;
    }

    @Override
    public Role findByRole(String role) {
        if (role==null) throw new IllegalArgumentException("Role can not be null!");
        return this.roleRepo.findByRole(role);
    }

    @Override
    public Role save(Role role) {
        if (role==null) throw new IllegalArgumentException("Role can not be null!");
        return this.roleRepo.save(role);
    }

    @Override
    public List<Role> findByUser(Long userId) throws ForbiddenException, UserNotFoundException {
        User user = this.userRepo.findOne(userId);
        if (user == null) throw new UserNotFoundException("Could not find user with id " + userId);
        return user.getRoles();
    }
}
