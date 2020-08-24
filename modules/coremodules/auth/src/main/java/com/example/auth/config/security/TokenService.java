package com.example.auth.config.security;

import com.example.auth.entities.UserAuth;
import com.example.common.misc.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class TokenService {
    private final TokenStore tokenStore;
    private final AuthorizationServerEndpointsConfiguration configuration;

    @Value("${auth.client.id}")
    private String clientId;

    @Autowired
    public TokenService(@Qualifier("tokenStore") TokenStore tokenStore, AuthorizationServerEndpointsConfiguration configuration) {
        this.tokenStore = tokenStore;
        this.configuration = configuration;
    }

    public void revokeAuthentication(UserAuth auth) {
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(clientId, auth.getUsername());
        for (OAuth2AccessToken token : tokens) {
            revokeToken(token.getValue());
        }
    }

    public OAuth2AccessToken createAccessToken(UserAuth userAuth) {

        Map<String, String> requestParameters = new HashMap<>();
        Set<String> scope = new HashSet<>();
        scope.add("read write trust");
        Set<String> resourceIds = new HashSet<>();
        Set<String> responseTypes = new HashSet<>();
        responseTypes.add("code");
        Map<String, Serializable> extensionProperties = new HashMap<>();

        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, this.clientId,
                userAuth.getAuthorities(), userAuth.isEnabled(), scope,
                resourceIds, null, responseTypes, extensionProperties);


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userAuth, null, userAuth.getAuthorities());
        OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, authenticationToken);
        AuthorizationServerTokenServices tokenService = configuration.getEndpointsConfigurer().getTokenServices();
        return tokenService.createAccessToken(auth);
    }

    public OAuth2AccessToken getAccessToken(String username) {
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(clientId, username);
        if (tokens != null && !tokens.isEmpty())
            return Commons.getLastElement(tokens);
        return null;
    }

    private boolean revokeToken(String tokenValue) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null) {
            return false;
        }
        if (accessToken.getRefreshToken() != null) {
            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        }
        tokenStore.removeAccessToken(accessToken);
        return true;
    }

}
