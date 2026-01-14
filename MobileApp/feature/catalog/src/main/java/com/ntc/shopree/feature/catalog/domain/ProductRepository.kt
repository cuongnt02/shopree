package com.ntc.shopree.feature.catalog.domain

import com.ntc.shopree.core.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>

    suspend fun findProductsByName(name: String): List<Product>

    // TODO: Should have get a product by a slug, not from its name
    suspend fun getProduct(slug: String): Product
}

