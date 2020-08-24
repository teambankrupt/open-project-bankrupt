package com.example.app.domains.promotions.models.entities;


import com.example.coreweb.domains.base.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "promotions", schema = "app")
public class Promo extends BaseEntity {
    @NotNull
    @Column(nullable = false)
    private String title;
    @Column(length = 2000)
    private String description;
    private String url;
    private String promoImage;
    private long viewCount;
    private long clickCount;
    private String priority;
    private String textColor;
    private String backgroundColor;
    private boolean active;

    public enum Priority {
        NORMAL("normal"),
        HIGH("high");

        private String value;

        Priority(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void increaseClickCount() {
        this.clickCount++;
    }

    public String getPromoImage() {
        return promoImage;
    }

    public void setPromoImage(String promoImage) {
        this.promoImage = promoImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTextColor() {
        if (textColor == null) return "#FFFFFF";
        if (!textColor.contains("#"))
            return "#" + textColor;
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBackgroundColor() {
        if (backgroundColor == null) return "#000000";
        if (!backgroundColor.contains("#"))
            return "#" + backgroundColor;
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getClickCount() {
        return clickCount;
    }

    public void setClickCount(long clickCount) {
        this.clickCount = clickCount;
    }
}
