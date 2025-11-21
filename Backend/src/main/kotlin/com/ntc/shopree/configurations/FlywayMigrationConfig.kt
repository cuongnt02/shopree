package com.ntc.shopree.configurations

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayMigrationConfig {
    @Bean fun flywayCleanMigrationStrategy() =  FlywayMigrationStrategy {
        it.clean()
        it.migrate()
    }

}
