package com.example.webservice.domains.users.repositories;


import com.example.webservice.domains.users.models.entities.AcValidationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AcValidationTokenRepository extends JpaRepository<AcValidationToken, Long> {
    AcValidationToken findFirstByTokenOrderByIdDesc(String token);

    AcValidationToken findFirstByPhoneOrderByIdDesc(String phone);
    int countByUserIdAndCreatedBetween(Long id, Date fromDate, Date toDate);
}
