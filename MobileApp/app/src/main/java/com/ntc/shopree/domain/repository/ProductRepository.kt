package com.ntc.shopree.domain.repository

import com.ntc.shopree.domain.models.Product


interface ProductRepository {
    // TODO: Move to suspend fun cause it's a blocking call
    fun getProducts(): List<Product>
}