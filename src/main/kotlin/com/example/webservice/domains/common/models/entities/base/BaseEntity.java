package com.example.webservice.domains.common.models.entities.base;

import com.example.webservice.commons.utils.DateUtil;
import com.example.webservice.config.security.SecurityConfig;
import com.example.webservice.domains.users.models.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @OneToOne
    private User createdBy;
    
    @OneToOne
    private User updatedBy;

    @PrePersist
    private void onBasePersist() {
        this.created = new Date();
        this.lastUpdated = new Date();
        this.createdBy = getCurrentUser();
    }

    @PreUpdate
    private void onBaseUpdate() {
        this.lastUpdated = new Date();
        this.updatedBy = getCurrentUser();
    }

    @JsonIgnore
    public User getCurrentUser() {
        return SecurityConfig.getCurrentUser();
    }

    public String getReadableDate(Date date) {
        return DateUtil.getReadableDate(date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getCreated() {
        return created;
    }


    public Date getLastUpdated() {
        return lastUpdated;
    }


    @JsonIgnore
    public User getCreatedBy() {
        return createdBy;
    }

    @JsonIgnore
    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
