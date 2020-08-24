package com.example.app.domains.promotions.services;


import com.example.app.domains.promotions.models.entities.Promo;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.unknown.UnknownException;
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
