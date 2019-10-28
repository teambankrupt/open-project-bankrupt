package com.example.webservice.repositories;

import com.example.webservice.entities.ClientConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientConfigRepository extends JpaRepository<ClientConfiguration, Long> {

}
