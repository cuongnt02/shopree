package com.ntc.data

import com.ntc.shopree.model.Category
import com.ntc.shopree.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProductRepository : CrudRepository<Product, UUID> {
    fun findByCategory(category: Category): List<Product>


    fun findBySlug(slug: String): Product
}
