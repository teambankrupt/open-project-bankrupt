package com.example.webservice.entities;

import com.example.webservice.entities.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "m_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "email"})
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity implements UserDetails {
    @NotNull
    @NotEmpty
    private String name;

    @Column(unique = true, nullable = false)
    @NotNull
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String username;

    @Column(unique = true)
    @Email
    private String email;

    @Column
    private String userType;

    @Column(unique = true, nullable = false)
    @NotNull
    @Size(min = 11)
    @JsonProperty("phone")
    private String phoneNumber;

    @NotEmpty
    @NotNull
    @Size(min = 6, max = 100, message = "Password must be between 6 to 100 characters!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 512, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Role> roles;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean enabled = true;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean accountNonExpired = true;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean accountNonLocked = true;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean credentialsNonExpired = true;

    @PrePersist
    private void onPrePersist() {
//        if (roles == null || roles.isEmpty())
//            grantRole(new Role(Role.ERole.ROLE_USER));
        if (this.username == null) this.setUsername(this.getPhoneNumber());
    }


    public void grantRole(Role role) {
        if (this.roles == null)
            this.roles = new ArrayList<>();
        // check if user already has that role
        if (!hasRole(role.getRole()) && !role.isAdmin())
            this.roles.add(role);
    }

    public void changeRole(Role role) {
        if (role == null || role.getRole().equals(Role.ERole.ROLE_ADMIN.toString())) return;
        this.roles = new ArrayList<>();
        this.roles.add(role);
    }

    public boolean hasRole(String role) {
        return this.roles != null && this.roles.stream()
                .filter(r -> r.getRole().trim().equals(role.trim()))
                .count() > 0;
    }

    @JsonIgnore
    public boolean isOnlyUser() {
        return this.roles.size() == 1 && hasRole(Role.ERole.ROLE_USER.toString());
    }

    @JsonIgnore
    public boolean isDriver() {
        return this.hasRole(Role.ERole.ROLE_DRIVER.toString());
    }

    @JsonIgnore
    public boolean isAdmin() {
        return this.hasRole(Role.ERole.ROLE_ADMIN.toString());
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Role role : this.roles) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
            authorityList.add(authority);
        }
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}