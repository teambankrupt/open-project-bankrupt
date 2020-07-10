package com.example.auth.entities;

import com.example.auth.enums.Privileges;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "roles_privileges", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "privilege_id", referencedColumnName = "id")})
    private List<Privilege> privileges;

    @Column(nullable = false)
    private boolean restricted = true;

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
        // TODO: add loggedinusername
//        this.createdBy = this.getLoggedInUsername();
        this.uuid = UUID.randomUUID().toString();
    }

    @PreUpdate
    private void onBaseUpdate() {
        this.updatedAt = new Date();
//        this.updatedBy = this.getLoggedInUsername();
    }

    public boolean isAdmin() {
        return privileges != null && this.privileges.stream().anyMatch(privilege -> Privileges.ADMINISTRATION.name().equals(privilege.getName()));
    }

    public boolean isSameAs(Role role) {
        if (role == null) return false;
        return role.id.equals(this.id);
    }

    public boolean hasPrivilege(Long privilegeId) {
        if (privilegeId == null) return false;
        return this.privileges.stream().anyMatch(p -> p.getId().equals(privilegeId));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public boolean isDeleted() {
        return deleted;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
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

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
