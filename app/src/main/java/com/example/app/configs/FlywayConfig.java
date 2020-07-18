package com.example.app.configs;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {
    private final DataSource dataSource;

    public FlywayConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean(initMethod = "migrate")
    Flyway flyway() {
        return Flyway.configure()
//                .outOfOrder(true)
                .baselineOnMigrate(true)
                .dataSource(this.dataSource).load();
    }

}
