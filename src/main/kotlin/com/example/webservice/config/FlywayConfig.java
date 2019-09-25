package com.example.webservice.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {
    @Autowired
    DataSource dataSource;

    @Bean(initMethod = "migrate")
    Flyway flyway() {
        return Flyway.configure()
                .dataSource(this.dataSource).load();
    }

}
