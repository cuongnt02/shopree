package com.ntc.shopree.core.model.repository

import com.ntc.shopree.core.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun findProductsByName(name: String): List<Product>
    suspend fun getProduct(slug: String): Product
}
