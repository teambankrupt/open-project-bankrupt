package com.example.auth.services;

import com.example.auth.domains.models.entities.Privilege;
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

    public List<Privilege> getAuthorities() {
//        String sql = "SELECT p.name,p.label,p.description FROM privileges p  WHERE p.deleted=false";
        String sql = "SELECT a FROM Privilege a  WHERE a.deleted=false";
        Query query = this.entityManager.createQuery(sql);

        List<Privilege> authorities = query.getResultList();
        return authorities;
    }


    private List<Privilege> populateAccessUrls(List<Privilege> authorities) {
        if (authorities == null) return new ArrayList<>();
        return authorities.stream().peek(privilege -> {
            //TODO: query authorities from database
            privilege.setAccessUrls(new ArrayList<>());
        }).collect(Collectors.toList());
    }

}
