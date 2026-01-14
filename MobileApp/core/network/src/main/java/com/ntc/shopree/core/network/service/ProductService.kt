package com.ntc.shopree.core.network.service

import com.ntc.shopree.core.model.Product

interface ProductService {
    suspend fun getProducts(): List<Product>

    suspend fun getProductsByName(name: String): List<Product>
    fun getProduct(slug: String): com.ntc.shopree.core.model.Product
}



