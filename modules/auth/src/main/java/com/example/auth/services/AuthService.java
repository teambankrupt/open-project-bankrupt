package com.example.auth.services;

import com.example.auth.domains.models.entities.Privilege;
import com.example.auth.domains.models.entities.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private final EntityManager entityManager;

    @Autowired
    public AuthService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserAuth findAuthUser(String username) {
        if (username == null) return null;
        String sql = "SELECT u FROM UserAuth u WHERE u.username=:username AND u.deleted=FALSE";
        Query query = this.entityManager.createQuery(sql);
        query.setParameter("username", username);
        UserAuth auth;
        try {
            auth = (UserAuth) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return auth;
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
