package com.example.auth.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "m_users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserAuth implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String password;
    private String phone;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "m_users_roles", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "roles_id", referencedColumnName = "id")})
    private List<Role> roles;

    private boolean enabled = true;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    private boolean deleted;

//    public UserAuth(User user) {
//        if (user == null) throw new IllegalArgumentException("User can not be null!");
//        this.id = user.getId();
//        this.name = user.getName();
//        this.username = user.getUsername();
//        this.password = user.getPassword();
//        this.phone = user.getPhone();
//        this.email = user.getEmail();
//        this.enabled = user.isEnabled();
//        this.roles = user.getRoles();
//        this.accountNonExpired = user.isAccountNonExpired();
//        this.accountNonLocked = user.isAccountNonLocked();
//        this.credentialsNonExpired = user.isCredentialsNonExpired();
//        this.admin = user.isAdmin();
//    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public UserAuth() {
    }

    public UserAuth(Long id, String name, String username, String password,
                    String phone, String email,
                    boolean enabled, boolean accountNonExpired, boolean accountNonLocked,
                    boolean credentialsNonExpired) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isAdmin() {
        return this.roles != null &&
                this.roles.stream().anyMatch(Role::isAdmin);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.roles == null) this.roles = new ArrayList<>();
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Role group : this.roles) {
            if (group.getPrivileges() == null) continue;
            authorityList.addAll(
                    group.getPrivileges().stream()
                            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                            .collect(Collectors.toList())
            );
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}