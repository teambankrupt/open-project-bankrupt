package com.example.auth.config.security.oauth;

import com.example.auth.entities.Privilege;
import com.example.auth.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final TokenStore tokenStore;
    private final AuthService authService;

    @Autowired
    public ResourceServerConfig(TokenStore tokenStore, AuthService authService) {
        this.tokenStore = tokenStore;
        this.authService = authService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry r = http
                .antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/api/v1/register/**",
                        "/api/v1/search/**",
                        "/api/v2/search/**",
                        "/api/v1/login**",
                        "/oauth/token**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/sw/**",
                        "/api/v1/resetPassword/**",
                        "/api/v1/checkTokenValidity",
                        "/api/v1/promos/**",
                        "/swagger-ui.html",
                        "/api/v1/profiles/user/snap/*"

                )
                .permitAll()
                .antMatchers(
                        "/api/v1/admin/**"
                )
                .hasAnyAuthority("ADMINISTRATION");

        for (Privilege a : this.authService.getAuthorities())
            r.antMatchers(a.accessesArr()).hasAnyAuthority(a.getName());

        r.anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(this.tokenStore);
    }
}
