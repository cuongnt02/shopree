package com.ntc.shopree.seeder

import com.ntc.data.CategoryRepository
import com.ntc.data.UserRepository
import com.ntc.data.VendorRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(4)
class ProductsSeeder(
    private val productRepository: UserRepository,
    private val vendorRepository: VendorRepository,
    private val categoryRepository: CategoryRepository
): CommandLineRunner {
    override fun run(vararg args: String) {
        productRepository.deleteAll()

    }
}