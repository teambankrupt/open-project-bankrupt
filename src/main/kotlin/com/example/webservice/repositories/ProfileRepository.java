package com.example.webservice.repositories;

import com.example.webservice.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,Long>{
    Profile findByUserUsername(String username);
    Profile findByUserId(Long userId);

}
