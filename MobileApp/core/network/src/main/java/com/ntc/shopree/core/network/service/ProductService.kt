package com.ntc.shopree.core.network.service

import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.network.dto.ProductResponse

interface ProductService {
    suspend fun getProducts(): List<ProductResponse>

    suspend fun getProductsByName(name: String): List<ProductResponse>
    suspend fun getProduct(slug: String): ProductResponse
}



