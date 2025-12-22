package com.ntc.shopree

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    scanBasePackages = ["com.ntc.shopree", "com.ntc.security", "com.ntc.api", "com.ntc.data", "com.ntc.service"]
)
@EnableJpaRepositories(basePackages = ["com.ntc.data"])
@EntityScan(basePackages = ["com.ntc.shopree.model"])
class ShopreeApplication

fun main(args: Array<String>) {
	runApplication<ShopreeApplication>(*args)
}
