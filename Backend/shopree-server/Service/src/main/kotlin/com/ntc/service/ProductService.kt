package com.ntc.service

import com.ntc.domain.model.Product

interface ProductService {
    fun getProductBySlug(slug: String): Product?
    fun getProducts(): List<Product>
}