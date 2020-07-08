package com.example.application.domains.promotions.services;


import com.example.application.domains.promotions.models.entities.Promo;
import com.example.application.exceptions.forbidden.ForbiddenException;
import com.example.application.exceptions.invalid.InvalidException;
import com.example.application.exceptions.notfound.NotFoundException;
import com.example.application.exceptions.unknown.UnknownException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromoService {
    Page<Promo> findAll(int page);
    Promo save(Promo promo) throws InvalidException;
    Promo findOne(Long id) throws NotFoundException;
    List<Promo> getLatestPromotions();

    void notifyUser(Long promoId) throws NotFoundException, ForbiddenException, UnknownException, InvalidException, JsonProcessingException;
}
