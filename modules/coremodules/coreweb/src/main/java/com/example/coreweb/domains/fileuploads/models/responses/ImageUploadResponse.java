package com.example.coreweb.domains.fileuploads.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageUploadResponse {
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("thumb_url")
    private String thumbUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
