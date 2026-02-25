package com.ntc.shopree.seeder

import com.ntc.data.CategoryRepository
import com.ntc.data.ProductRepository
import com.ntc.data.VendorRepository
import com.ntc.domain.model.Product
import com.ntc.domain.model.Vendor
import com.ntc.domain.model.Category
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.util.Locale
import kotlin.random.Random

@Component
@Order(4)
class ProductsSeeder(
    private val productRepository: ProductRepository,
    private val vendorRepository: VendorRepository,
    private val categoryRepository: CategoryRepository
): CommandLineRunner {
    override fun run(vararg args: String) {
        val vendors: List<Vendor> = vendorRepository.findAll().toList()
        val categories: List<Category> = categoryRepository.findAll().toList()

        if (vendors.isEmpty() || categories.isEmpty()) {
            // Nothing to seed if dependencies are missing
            return
        }

        // Clear existing products before seeding
        productRepository.deleteAll()

        val adjectives = listOf(
            "Sleek", "Compact", "Durable", "Premium", "Eco", "Smart", "Portable", "Lightweight",
            "Advanced", "Classic", "Modern", "Wireless", "Ergonomic", "Pro", "Ultra"
        )
        val nouns = listOf(
            "Headphones", "Backpack", "Blender", "Camera", "Laptop Stand", "Yoga Mat", "Coffee Maker",
            "Water Bottle", "Desk Lamp", "Mouse", "Keyboard", "Sneakers", "Jacket", "Book", "Toys"
        )

        fun slugify(text: String): String = text
            .lowercase(Locale.getDefault())
            .replace("&", " and ")
            .replace("[^a-z0-9]+".toRegex(), "-")
            .trim('-')

        val images = listOf(
            "https://picsum.photos/seed/1/600/400",
            "https://picsum.photos/seed/2/600/400",
            "https://picsum.photos/seed/3/600/400",
            "https://picsum.photos/seed/4/600/400",
            "https://picsum.photos/seed/5/600/400"
        )

        val products = (1..50).map { idx ->
            val title = "${adjectives.random()} ${nouns.random()} ${idx}"
            val slug = slugify(title)
            val vendor = vendors.random()
            val category = categories.random()
            val image = images.random()

            Product(
                vendor = vendor,
                title = title,
                slug = slug,
                description = "Sample product $idx seeded for development and testing.",
                category = category,
                mainImage = image,
                tags = null, // left null to match current mapping (no @ElementCollection/@Json)
                pickupAvailable = Random.nextBoolean(),
            )
        }

        productRepository.saveAll(products)
    }
}