package com.example.app.configs;

import com.example.coreweb.interceptors.ActivityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private final ActivityInterceptor activityInterceptor;

    @Autowired
    public AppConfig(ActivityInterceptor activityInterceptor) {
        this.activityInterceptor = activityInterceptor;
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
