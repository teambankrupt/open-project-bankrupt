package com.example.webservice.config.security.oauth;

import com.example.webservice.domains.users.models.entities.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final TokenStore tokenStore;

    @Autowired
    public ResourceServerConfig(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
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
                        "/api/v1/search/users",
                        "/api/v1/stats/employee",
                        "/api/v1/users"
                )
                .hasAnyAuthority(Privilege.Privileges.ADMINISTRATION.toString())
                .antMatchers(
                        "/api/v1/admin/**"
                )
                .hasAnyAuthority(Privilege.Privileges.ADMINISTRATION.toString())
                .anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(this.tokenStore);
    }
}
