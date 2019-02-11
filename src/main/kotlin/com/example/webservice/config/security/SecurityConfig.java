package com.example.webservice.config.security;

import com.example.webservice.entities.Role;
import com.example.webservice.entities.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/fonts/**", "/adminlte/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/oauth/token",
                        "/api/v1/**",
                        "/rest/**",
                        "/",
                        "/login",
                        "/logout",
                        "/register",
                        "/resetPassword/**",
                        "/fonts/**",
                        "/js/**",
                        "/css/**",
                        "/images/**",
                        "/test"
                )
                .permitAll()
                .antMatchers("/admin/**").hasRole(Role.StringRole.AUTHORITY_ADMIN)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/admin/dashboard")
                .permitAll()
                .and()
                .rememberMe()
                .key("absym3h4uhd");
        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .permitAll();
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
////                .inMemoryAuthentication()
////                .withUser("admin").password("pass").roles("USER","HOTEL","ADMIN");
//                .userDetailsService(this.userDetailsService)
//                .passwordEncoder(PasswordUtil.getBCryptPasswordEncoder());
//    }


    public static void updateAuthentication(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    public static boolean isAuthenticated() {
        return getCurrentUser() != null;
    }
}
