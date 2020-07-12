package com.example.app.domains.promotions.controllers.promo.admin;

import com.example.app.domains.promotions.models.entities.Promo;
import com.example.app.domains.promotions.services.PromoService;
import com.example.common.exceptions.forbidden.ForbiddenException;
import com.example.common.exceptions.invalid.InvalidException;
import com.example.common.exceptions.notfound.NotFoundException;
import com.example.common.exceptions.unknown.UnknownException;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/admin/promos")
@Api(tags = "Promo [Admin]", description = "Promotions CRUD for admin")
public class PromoAdminController {

    private final PromoService promoService;

    @Autowired
    public PromoAdminController(PromoService promoService) {
        this.promoService = promoService;
    }

    @GetMapping("")
    private ResponseEntity getAllPromotions(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(this.promoService.findAll(page));
    }

    // Create promotion // deactivated by default for security purpose
    @PostMapping("")
    private ResponseEntity createPromo(@RequestBody Promo promo) throws InvalidException {
        if (promo != null) promo.setActive(false);
        return ResponseEntity.ok(this.promoService.save(promo));
    }

    @PatchMapping("")
    private ResponseEntity updatePromo(@RequestBody Promo promo) throws InvalidException, NotFoundException {
        if (promo == null || promo.getId() == null) return ResponseEntity.badRequest().build();
        Promo exPromo = this.promoService.findOne(promo.getId());
        if (exPromo == null) return ResponseEntity.noContent().build();
        exPromo.setTitle(promo.getTitle());
        exPromo.setDescription(promo.getDescription());
        exPromo.setUrl(promo.getUrl());
        exPromo.setPriority(promo.getPriority());
        exPromo.setTextColor(promo.getTextColor());
        exPromo.setBackgroundColor(promo.getBackgroundColor());
        exPromo.setActive(promo.isActive());
        exPromo = this.promoService.save(exPromo);
        return ResponseEntity.ok(exPromo);
    }

    // activate-deactivate a promotion
    @PatchMapping("/{id}/toggleActivation")
    private ResponseEntity activatePromo(@PathVariable("id") Long id) throws InvalidException, NotFoundException {
        Promo exPromo = this.promoService.findOne(id);
        if (exPromo == null) return ResponseEntity.noContent().build();
        exPromo.setActive(!exPromo.isActive());
        exPromo = this.promoService.save(exPromo);
        return ResponseEntity.ok(exPromo);
    }


    @PostMapping("/{id}/notifyUsers")
    @ResponseStatus(HttpStatus.OK)
    protected void sendNotification(@PathVariable("id") Long promoId) throws JsonProcessingException, InvalidException, ForbiddenException, NotFoundException, UnknownException {
        this.promoService.notifyUser(promoId);
    }

}
