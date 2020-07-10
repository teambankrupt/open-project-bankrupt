package com.example.auth.entities;

import com.example.auth.config.security.SecurityContext;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "privileges")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String label;

    private String description;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "privileges_access_urls")
    private List<String> accessUrls;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "uuid_str", nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false)
    private boolean deleted;

    @PrePersist
    private void onBasePersist() {
        this.createdAt = new Date();
        this.updatedAt = createdAt;
        this.createdBy = SecurityContext.getLoggedInUsername();
        this.uuid = UUID.randomUUID().toString();
    }

    @PreUpdate
    private void onBaseUpdate() {
        this.updatedAt = new Date();
        this.updatedBy = SecurityContext.getLoggedInUsername();
    }

    public Privilege() {
    }

    public Privilege(String name, String label) {
        this.name = name;
        this.label = label;
    }

    public String[] accessesArr() {
        String[] itemsArray = new String[accessUrls.size()];
        return accessUrls.toArray(itemsArray);
    }

    public String accessesStr() {
        return String.join(",", accessUrls);
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAccessUrls() {
        return accessUrls;
    }

    public void setAccessUrls(List<String> accessUrls) {
        this.accessUrls = accessUrls;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getUuid() {
        return uuid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
