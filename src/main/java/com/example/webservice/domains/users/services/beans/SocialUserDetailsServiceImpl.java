package com.example.webservice.domains.users.services.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author mir00r on 3/4/20
 * @project IntelliJ IDEA
 */
@Service
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public SocialUserDetails loadUserByUserId(String userName) throws UsernameNotFoundException {
        System.out.println("SocialUserDetailsServiceImpl.loadUserByUserId = " + userName);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
        return (SocialUserDetailsImpl) userDetails;
    }
}
