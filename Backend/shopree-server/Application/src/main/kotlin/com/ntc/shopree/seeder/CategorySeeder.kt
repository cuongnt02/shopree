package com.ntc.shopree.seeder

import com.ntc.shopree.model.Category
import com.ntc.data.CategoryRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.DependsOn
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(1)
class CategorySeeder(private val categoryRepository: CategoryRepository) : CommandLineRunner {

    override fun run(vararg args: String) {
        categoryRepository.deleteAll()
        val electronics: Category =
            categoryRepository.save(Category("Electronics", "electronics", null))
        val home: Category =
            categoryRepository.save(Category("Home & Kitchen", "home-kitchen", null))
        val fashion: Category =
            categoryRepository.save(Category("Fashion", "fashion", null))
        val beauty: Category =
            categoryRepository.save(Category("Beauty & Personal Care", "beauty-personal-care", null))
        val sports: Category =
            categoryRepository.save(Category("Sports & Outdoors", "sports-outdoors", null))
        val books: Category =
            categoryRepository.save(Category("Books", "books", null))
        val toys: Category =
            categoryRepository.save(Category("Toys & Games", "toys-games", null))
        val auto: Category =
            categoryRepository.save(Category("Automotive", "automotive", null))
        val groceries: Category =
            categoryRepository.save(Category("Groceries", "groceries", null))
        val pets: Category =
            categoryRepository.save(Category("Pet Supplies", "pet-supplies", null))

        categoryRepository.save(Category("Mobile Phones", "mobile-phones", electronics))
        categoryRepository.save(Category("Laptops", "laptops", electronics))
        categoryRepository.save(Category("Cameras", "cameras", electronics))

        categoryRepository.save(Category("Kitchen Appliances", "kitchen-appliances", home))
        categoryRepository.save(Category("Furniture", "furniture", home))

        categoryRepository.save(Category("Men Clothing", "men-clothing", fashion))
        categoryRepository.save(Category("Women Clothing", "women-clothing", fashion))

        categoryRepository.save(Category("Skincare", "skincare", beauty))
        categoryRepository.save(Category("Makeup", "makeup", beauty))

        categoryRepository.save(Category("Fitness Equipment", "fitness-equipment", sports))
        categoryRepository.save(Category("Cycling", "cycling", sports))

        categoryRepository.save(Category("Fiction Books", "fiction-books", books))
        categoryRepository.save(Category("Non-fiction Books", "non-fiction-books", books))

        categoryRepository.save(Category("Board Games", "board-games", toys))
        categoryRepository.save(Category("Action Figures", "action-figures", toys))

        categoryRepository.save(Category("Car Accessories", "car-accessories", auto))
        categoryRepository.save(Category("Motorcycle Parts", "motorcycle-parts", auto))

        categoryRepository.save(Category("Fresh Produce", "fresh-produce", groceries))
        categoryRepository.save(Category("Snacks", "snacks", groceries))

        categoryRepository.save(Category("Dog Supplies", "dog-supplies", pets))

    }
}