package com.example.webservice.domains.users.services.beans;

import com.example.webservice.domains.users.models.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author mir00r on 3/4/20
 * @project IntelliJ IDEA
 */
public class SocialUserDetailsImpl implements SocialUserDetails {

    private static final long serialVersionUID = 1L;
    private List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
    private User appUser;

    public SocialUserDetailsImpl(User appUser, List<String> roleNames) {
        this.appUser = appUser;
        for (String roleName : roleNames) {
            GrantedAuthority grant = new SimpleGrantedAuthority(roleName);
            this.list.add(grant);
        }
    }


    @Override
    public String getUserId() {
        return this.appUser.getId() + "";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.list;
    }

    @Override
    public String getPassword() {
        return this.appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.appUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
