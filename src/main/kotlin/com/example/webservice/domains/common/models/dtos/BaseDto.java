package com.example.webservice.domains.common.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class BaseDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(value = "created", access = JsonProperty.Access.READ_ONLY)
    private Date created;
    @JsonProperty(value = "last_updated", access = JsonProperty.Access.READ_ONLY)
    private Date lastUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
