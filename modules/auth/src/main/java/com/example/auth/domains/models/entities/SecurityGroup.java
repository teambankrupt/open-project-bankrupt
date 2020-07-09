package com.example.auth.domains.models.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class SecurityGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "roles_privileges", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "privilege_id", referencedColumnName = "id")})
    private List<Authority> authorities;

    @Column(nullable = false)
    private boolean restricted = true;

    private boolean deleted=false;

    public boolean isAdmin() {
        return authorities != null && this.authorities.stream().anyMatch(authority -> "ADMINISTRATION".equals(authority.getName()));
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

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
