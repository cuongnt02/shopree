package com.ntc.shopree.domain.repository

import com.ntc.shopree.domain.models.Product


interface ProductRepository {
    suspend fun getProducts(): List<Product>
}