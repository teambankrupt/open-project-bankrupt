package com.example.webservice.domains.promotions.controllers.promo;

import com.example.webservice.domains.promotions.models.entities.Promo;
import com.example.webservice.exceptions.invalid.InvalidException;
import com.example.webservice.exceptions.notfound.NotFoundException;
import com.example.webservice.domains.promotions.services.PromoService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/api/v1/promos")
public class PromoController {

    private final PromoService promoService;

    @Autowired
    public PromoController(PromoService promoService) {
        this.promoService = promoService;
    }

    @GetMapping("")
    private ResponseEntity getLatestPromos() {
        return ResponseEntity.ok(this.promoService.getLatestPromotions());
    }


    @PostMapping("/{id}/counters")
    private String countPromotionStats(@PathVariable("id") Long promoId,
                                       @RequestParam(value = "click", required = false) Boolean click,
                                       @RequestParam(value = "redirectUrl", required = false) String redirectUrl) throws InvalidException, NotFoundException {
        Promo promo = this.promoService.findOne(promoId);
        if (promo == null) return "";
        promo.increaseViewCount();
        if (click != null && click)
            promo.increaseClickCount();
        this.promoService.save(promo);
        if (redirectUrl == null || redirectUrl.isEmpty()) return null;
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/{id}/image")
    private ResponseEntity getImage(@PathVariable("id") Long promoId) throws NotFoundException, IOException {
        Promo promoImage = this.promoService.findOne(promoId);
        if (promoImage.getImagePath() == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        File file = new File(promoImage.getImagePath());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(FileUtils.readFileToByteArray(file));
    }
}
