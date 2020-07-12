package com.example.auth.services;

import com.example.auth.entities.Privilege;
import com.example.auth.entities.User;
import com.example.auth.repositories.UserRepo;
import com.example.common.exceptions.notfound.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final EntityManager entityManager;

    @Autowired
    public AuthService(UserRepo userRepo, EntityManager entityManager) {
        this.userRepo = userRepo;
        this.entityManager = entityManager;
    }

    public boolean existsByUsername(String username) throws UserNotFoundException {
        return this.findAuthUser(username) != null;
    }

    public User findAuthUser(String username) throws UserNotFoundException {
        return this.userRepo.find(username).orElseThrow(() -> new UserNotFoundException("Could not find user with username: " + username));
    }

    public List<Privilege> getAuthorities() {
        String sql = "SELECT a FROM Privilege a  WHERE a.deleted=false";
        Query query = this.entityManager.createQuery(sql);

        List<Privilege> authorities;
        try {
            authorities = query.getResultList();
        } catch (Exception e) {
            authorities = new ArrayList<>();
        }
        return authorities;
    }

}
