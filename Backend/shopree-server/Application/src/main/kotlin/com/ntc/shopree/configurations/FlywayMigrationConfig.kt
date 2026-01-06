package com.ntc.shopree.configurations

import org.springframework.boot.flyway.autoconfigure.FlywayMigrationStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayMigrationConfig {
    @Bean fun flywayCleanMigrationStrategy() = FlywayMigrationStrategy {
        it.clean()
        it.migrate()
    }

}
