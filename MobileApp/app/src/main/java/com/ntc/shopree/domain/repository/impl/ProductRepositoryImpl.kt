package com.ntc.shopree.domain.repository.impl

import com.ntc.shopree.data.remote.service.ProductService
import com.ntc.shopree.domain.models.Product
import com.ntc.shopree.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productService: ProductService) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return productService.getProducts()
    }
}