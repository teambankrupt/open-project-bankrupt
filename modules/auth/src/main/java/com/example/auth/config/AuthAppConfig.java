package com.example.auth.config;

import com.example.auth.listeners.AuthenticationFailureEventListener;
import com.example.auth.listeners.AuthenticationSuccessEventListener;
import com.example.auth.services.CustomUserDetailsService;
import com.example.auth.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class AuthAppConfig {
    private final AuthenticationSuccessEventListener successEventListener;
    private final AuthenticationFailureEventListener failureEventListener;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthAppConfig(AuthenticationSuccessEventListener successEventListener, AuthenticationFailureEventListener failureEventListener, CustomUserDetailsService userDetailsService) {
        this.successEventListener = successEventListener;
        this.failureEventListener = failureEventListener;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public ApplicationListener loginSuccessListener() {
        return this.successEventListener;
    }

    @Bean
    public ApplicationListener loginFailureListener() {
        return this.failureEventListener;
    }


    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(PasswordUtil.getBCryptPasswordEncoder());
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setDispatchOptionsRequest(true);
        return dispatcherServlet;
    }

}
