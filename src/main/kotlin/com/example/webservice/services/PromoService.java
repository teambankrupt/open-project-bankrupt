package com.example.webservice.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.webservice.entities.Promo;
import com.example.webservice.exceptions.forbidden.ForbiddenException;
import com.example.webservice.exceptions.invalid.ImageInvalidException;
import com.example.webservice.exceptions.invalid.InvalidException;
import com.example.webservice.exceptions.notfound.NotFoundException;
import com.example.webservice.exceptions.unknown.UnknownException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.LimitExceededException;
import java.io.IOException;
import java.util.List;

public interface PromoService {
    Page<Promo> findAll(int page);
    Promo save(Promo promo) throws InvalidException;
    Promo findOne(Long id) throws NotFoundException;
    List<Promo> getLatestPromotions();

    void notifyUser(Long promoId) throws NotFoundException, ForbiddenException, UnknownException, InvalidException, JsonProcessingException;

    void uploadPhoto(Long promoId, MultipartFile multipartFile) throws ImageInvalidException, LimitExceededException, NotFoundException, IOException;
}
