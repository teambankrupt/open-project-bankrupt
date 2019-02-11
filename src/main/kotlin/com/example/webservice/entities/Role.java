package com.example.webservice.entities;

import com.example.webservice.entities.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    private String name;
    private String role;

    public Role() {
    }

    public Role(ERole eRole) {
        this.name = eRole.getValue();
        this.role = eRole.toString();
    }


    public enum ERole {
        ROLE_ADMIN("Admin"),
        ROLE_DRIVER("Driver"),
        ROLE_USER("User");

        String value;

        ERole(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static ERole getERole(String role) {
        for (ERole eRole : ERole.values()) {
            if (eRole.toString().equals(role))
                return eRole;
        }
        return ERole.ROLE_USER;
    }

    public static ERole getERoleFromRoleName(String roleName) {
        if (roleName == null) roleName = "";
        for (ERole eRole : ERole.values()) {
            if (eRole.getValue().trim().toLowerCase().equals(roleName.trim().toLowerCase()))
                return eRole;
        }
        return ERole.ROLE_USER;
    }

    public static class StringRole {
        public static final String ROLE_USER = "ROLE_USER";
        public static final String ROLE_DRIVER = "ROLE_DRIVER";
        public static final String ROLE_ADMIN = "ROLE_ADMIN";

        public static final String AUTHORITY_USER = "USER";
        public static final String AUTHORITY_DRIVER = "DRIVER";
        public static final String AUTHORITY_ADMIN = "ADMIN";
    }

    @JsonIgnore
    public boolean isAdmin() {
        return this.role != null && this.role.equals(ERole.ROLE_ADMIN.toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
