package com.example.auth.config.security.oauth;

import com.example.auth.entities.UserAuth;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

public class JwtTokenConverter extends JwtAccessTokenConverter {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        UserAuth user = (UserAuth) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();

        additionalInfo.put("id", user.getId());
        additionalInfo.put("name", user.getName());
        additionalInfo.put("username", user.getUsername());

        if (user.getPhone() != null)
            additionalInfo.put("phone", user.getPhone());
        if (user.getEmail() != null)
            additionalInfo.put("email", user.getEmail());

//        additionalInfo.put("authorities", user.getAuthorities());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return super.enhance(accessToken, authentication);
    }


}
