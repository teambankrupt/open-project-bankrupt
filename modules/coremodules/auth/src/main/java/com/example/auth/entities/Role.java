package com.example.auth.entities;

import com.example.auth.enums.Privileges;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "roles", schema = "auth")
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "roles_privileges", schema = "auth", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "privilege_id", referencedColumnName = "id")})
    private List<Privilege> privileges;

    @Column(nullable = false)
    private boolean restricted = true;

    public boolean isAdmin() {
        return privileges != null && this.privileges.stream().anyMatch(privilege -> Privileges.ADMINISTRATION.name().equals(privilege.getName()));
    }

    public boolean isSameAs(Role role) {
        if (role == null) return false;
        return role.getId().equals(this.getId());
    }

    public boolean hasPrivilege(Long privilegeId) {
        if (privilegeId == null) return false;
        return this.privileges.stream().anyMatch(p -> p.getId().equals(privilegeId));
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


}
