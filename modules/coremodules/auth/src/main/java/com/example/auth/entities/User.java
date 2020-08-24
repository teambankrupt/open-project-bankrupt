package com.example.auth.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "m_users", schema = "auth")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String gender;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(length = 512, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "m_users_roles", schema = "auth", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "roles_id", referencedColumnName = "id")})
    private List<Role> roles;

    private boolean enabled = true;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    public User() {
    }

    public User(UserAuth auth) {
        if (auth == null) throw new IllegalArgumentException("User can not be null!");
        this.setId(auth.getId());
        this.name = auth.getName();
        this.username = auth.getUsername();
        this.password = auth.getPassword();
        this.phone = auth.getPhone();
        this.email = auth.getEmail();
        this.enabled = auth.isEnabled();
        this.roles = auth.getRoles();
        this.accountNonExpired = auth.isAccountNonExpired();
        this.accountNonLocked = auth.isAccountNonLocked();
        this.credentialsNonExpired = auth.isCredentialsNonExpired();
    }

    public void grantRole(Role role) {
        if (this.roles == null)
            this.roles = new ArrayList<>();
        // check if user already has that role
        if (!hasRole(role) && !role.isAdmin())
            this.roles.add(role);
    }

    public boolean hasRole(Role role) {
        return this.roles != null && this.roles.stream().anyMatch(r -> r.isSameAs(role));
    }

    public boolean isAdmin() {
        return this.roles != null &&
                this.roles.stream().anyMatch(Role::isAdmin);
    }

    public boolean canLogin() {
        return this.enabled
                && this.accountNonExpired
                && this.accountNonLocked
                && this.credentialsNonExpired;
    }


    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
