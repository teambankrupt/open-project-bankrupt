package com.example.app.domains.profiles.repositories;


import com.example.app.domains.profiles.models.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
    Profile findByUserUsername(String username);
    Profile findByUserId(Long userId);

}
