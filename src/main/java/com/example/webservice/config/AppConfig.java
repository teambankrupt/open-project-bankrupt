package com.example.webservice.config;
import com.example.webservice.commons.utils.PasswordUtil;
import com.example.webservice.domains.users.services.beans.CustomUserDetailsService;
import com.example.webservice.interceptors.ActivityInterceptor;
import com.example.webservice.listeners.AuthenticationFailureEventListener;
import com.example.webservice.listeners.AuthenticationSuccessEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private final AuthenticationSuccessEventListener successEventListener;
    private final AuthenticationFailureEventListener failureEventListener;
    private final ActivityInterceptor activityInterceptor;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AppConfig(AuthenticationSuccessEventListener successEventListener, AuthenticationFailureEventListener failureEventListener, ActivityInterceptor activityInterceptor, CustomUserDetailsService customUserDetailsService) {
        this.successEventListener = successEventListener;
        this.failureEventListener = failureEventListener;
        this.activityInterceptor = activityInterceptor;
        this.userDetailsService = customUserDetailsService;
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


    @Bean
    public ApplicationListener loginSuccessListener() {
        return this.successEventListener;
    }

    @Bean
    public ApplicationListener loginFailureListener() {
        return this.failureEventListener;
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    // REGISTER INTERCEPTER
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        super.addInterceptors(registry);
        registry.addInterceptor(activityInterceptor);
    }

}
