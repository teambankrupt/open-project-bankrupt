package com.example.app.domains.promotions.controllers.promo;

import com.example.app.domains.promotions.models.entities.Promo;
import com.example.app.domains.promotions.services.PromoService;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/promos")
@Api(tags = "Promo", description = "Fetching latest promotions, handling click events etc")
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

}
