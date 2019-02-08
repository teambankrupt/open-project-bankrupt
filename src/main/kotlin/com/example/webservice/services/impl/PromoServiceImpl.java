package com.example.webservice.services.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.webservice.commons.PageAttr;
import com.example.webservice.commons.utils.ImageValidator;
import com.example.webservice.commons.utils.Validator;
import com.example.webservice.entities.Promo;
import com.example.webservice.entities.firebase.NotificationData;
import com.example.webservice.entities.firebase.PushNotification;
import com.example.webservice.entities.pojo.UploadProperties;
import com.example.webservice.exceptions.forbidden.ForbiddenException;
import com.example.webservice.exceptions.invalid.ImageInvalidException;
import com.example.webservice.exceptions.invalid.InvalidException;
import com.example.webservice.exceptions.notfound.NotFoundException;
import com.example.webservice.exceptions.unknown.UnknownException;
import com.example.webservice.repositories.PromoRepository;
import com.example.webservice.services.FileUploadService;
import com.example.webservice.services.NotificationService;
import com.example.webservice.services.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.LimitExceededException;
import java.io.IOException;
import java.util.List;

@Service
public class PromoServiceImpl implements PromoService {

    private final PromoRepository promoRepo;
    private final NotificationService notificationService;
    private final FileUploadService fileUploadService;

    @Autowired
    public PromoServiceImpl(PromoRepository promoRepo, NotificationService notificationService, FileUploadService fileUploadService) {
        this.promoRepo = promoRepo;
        this.notificationService = notificationService;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public Page<Promo> findAll(int page) {
        return this.promoRepo.findAll(PageAttr.getPageRequest(page));
    }

    @Override
    public Promo save(Promo promo) throws InvalidException {
        if (promo == null) throw new IllegalArgumentException("Promo can not be empty!");
        if (Validator.nullOrEmpty(promo.getTitle()))
            throw new InvalidException("Title can not be null or empty!");
        return this.promoRepo.save(promo);
    }

    @Override
    public Promo findOne(Long id) throws NotFoundException {
        Promo promo = this.promoRepo.findOne(id);
        if (promo == null) throw new NotFoundException("Could not find promo with id " + id);
        return promo;
    }

    @Override
    public List<Promo> getLatestPromotions() {
        return this.promoRepo.findByActiveOrderByIdDesc(true);
    }

    @Override
    public void notifyUser(Long promoId) throws NotFoundException, ForbiddenException, UnknownException, InvalidException, JsonProcessingException {
        Promo promo = this.promoRepo.findOne(promoId);
        if (promo == null) throw new NotFoundException("Cound not find promo with id: " + promoId);
        if (!promo.isActive()) throw new ForbiddenException("Can not notify users. Promotion is not active!");
        NotificationData data = new NotificationData();
        data.setTitle(promo.getTitle());
        String description = promo.getDescription();
        if (description == null) description = "";
        String brief = description.substring(0, Math.min(description.length(), 100));
        data.setMessage(brief);
        if (Promo.Priority.NORMAL.getValue().equals(promo.getPriority()))
            data.setType(PushNotification.Type.PROMOTION.getValue());
        else if (Promo.Priority.HIGH.getValue().equals(promo.getPriority()))
            data.setType(PushNotification.Type.ALERT.getValue());

        PushNotification notification = new PushNotification(null, data);
        notification.setTo("/topics/users");
        this.notificationService.sendNotification(notification);
    }

    @Override
    public void uploadPhoto(Long promoId, MultipartFile multipartFile) throws ImageInvalidException, LimitExceededException, NotFoundException, IOException {
        if (!ImageValidator.isImageValid(multipartFile))
            throw new ImageInvalidException("Invalid image!");
        if (multipartFile.getSize() > 1000000)
            throw new LimitExceededException("Image size should be less than 1 mb");
        Promo promo = this.findOne(promoId);
        UploadProperties properties = this.fileUploadService.uploadFile(multipartFile, UploadProperties.NameSpaces.PROMOTIONS.getValue(), String.valueOf(promo.getId()), false);
        promo.setImagePath(properties.getFilePath());
        this.promoRepo.save(promo);
    }

}
