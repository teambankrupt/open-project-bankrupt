package com.example.auth.services;

import com.example.auth.domains.models.entities.Authority;
import com.example.auth.domains.models.entities.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final EntityManager entityManager;

    @Autowired
    public AuthService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserAuth findAuthUser(String username) {
        String sql = "SELECT u FROM UserAuth u WHERE u.username=:username AND u.deleted=FALSE";
        Query query = this.entityManager.createQuery(sql);
        query.setParameter("username", username);
        UserAuth auth = (UserAuth) query.getSingleResult();

        return auth;
    }

    public List<Authority> getAuthorities() {
//        String sql = "SELECT p.name,p.label,p.description FROM privileges p  WHERE p.deleted=false";
        String sql = "SELECT a FROM Authority a  WHERE a.deleted=false";
        Query query = this.entityManager.createQuery(sql);

        List<Authority> authorities = query.getResultList();
        return authorities;
    }


    private List<Authority> populateAccessUrls(List<Authority> authorities) {
        if (authorities == null) return new ArrayList<>();
        return authorities.stream().peek(authority -> {
            //TODO: query authorities from database
            authority.setAccessUrls(new ArrayList<>());
        }).collect(Collectors.toList());
    }

}
