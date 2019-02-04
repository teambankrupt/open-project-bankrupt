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
//                .antMatcher("/api/v1/**")
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/register/**",
                        "/dev/register",
                        "/dev/client/credentials",
                        "/api/v1/users/create",
                        "/api/v1/orders/create",
                        "/api/v1/search/**",
                        "/api/v2/search/**",
                        "/api/v1/apartments/**",
                        "/api/v1/buildings/byApartment/**",
                        "/login**",
                        "/oauth/token**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/sw/**",
                        "/resetPassword/**",
                        "/checkTokenValidity",
                        "/api/v1/apps/all",
                        "/api/v1/promos/**",
                        "/swagger-ui.html",
                        "/api/v1/profiles/user/snap/*"

                )
                .permitAll()
                .antMatchers(
                        "/api/v1/search/users",
                        "/api/v1/stats/employee"
                )
                .hasAnyAuthority(Role.StringRole.ROLE_ADMIN, Role.StringRole.ROLE_EMPLOYEE, Role.StringRole.ROLE_FIELD_EMPLOYEE)
                .antMatchers(
                        "/api/v1/admin/**"
                )
                .hasAnyAuthority(Role.StringRole.ROLE_ADMIN, Role.StringRole.ROLE_EMPLOYEE)
                .antMatchers(
                        "/api/v1/users"
                )
                .hasAuthority(Role.StringRole.ROLE_ADMIN)
                .anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll();
    }
}
