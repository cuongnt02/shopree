package com.ntc.shopree.core.network.service

import com.ntc.shopree.core.model.Product

interface ProductService {
    suspend fun getProducts(): List<Product>
}



