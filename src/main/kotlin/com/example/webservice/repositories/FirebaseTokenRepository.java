package com.example.webservice.repositories;

import com.example.webservice.entities.firebase.FirebaseUserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirebaseTokenRepository extends JpaRepository<FirebaseUserToken, Long> {
    FirebaseUserToken findByUserId(Long userId);
}
