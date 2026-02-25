package com.ntc.service

import com.ntc.service.dto.ProductResponse

interface ProductService {
    fun getProductBySlug(slug: String): ProductResponse?
    fun getProducts(): List<ProductResponse>

    fun getProductsByName(name: String): List<ProductResponse>
}