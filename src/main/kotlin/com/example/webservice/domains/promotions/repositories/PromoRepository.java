package com.example.webservice.domains.promotions.repositories;


import com.example.webservice.domains.promotions.models.entities.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoRepository extends JpaRepository<Promo, Long> {
    List<Promo> findByActiveOrderByIdDesc(boolean isActive);
}
