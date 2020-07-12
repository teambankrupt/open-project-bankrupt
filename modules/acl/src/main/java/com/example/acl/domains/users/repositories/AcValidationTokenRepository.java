package com.example.acl.domains.users.repositories;


import com.example.acl.domains.users.models.entities.AcValidationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AcValidationTokenRepository extends JpaRepository<AcValidationToken, Long> {
    AcValidationToken findFirstByTokenOrderByIdDesc(String token);

    AcValidationToken findFirstByUsernameOrderByIdDesc(String phone);

    @Query("SELECT COUNT(t) FROM AcValidationToken t WHERE t.id=:id AND (t.createdAt BETWEEN :fromDate AND :toDate) AND t.deleted=false")
    int count(@Param("id") Long id, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
