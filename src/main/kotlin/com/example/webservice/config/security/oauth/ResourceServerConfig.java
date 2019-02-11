package com.example.webservice.config.security.oauth;

import com.example.webservice.entities.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    // TODO: secure url perfectly (some url need to secure at method level because of RequestMethod POST, PUT related to this url)
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/v1/**")
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
                        "/api/v1/stats/employee"
                )
                .hasAnyAuthority(Role.StringRole.ROLE_ADMIN)
                .antMatchers(
                        "/api/v1/admin/**"
                )
                .hasAnyAuthority(Role.StringRole.ROLE_ADMIN)
                .antMatchers(
                        "/api/v1/users"
                )
                .hasAuthority(Role.StringRole.ROLE_ADMIN)
                .anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll();
    }
}
