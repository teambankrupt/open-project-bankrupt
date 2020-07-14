package com.example.app.domains.remoteconfig.repositories;

import com.example.app.domains.remoteconfig.models.entities.RemoteConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemoteConfigRepository extends JpaRepository<RemoteConfig, Long> {

}
