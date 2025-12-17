package com.ntc.shopree.domain.repository.impl

import com.ntc.shopree.domain.models.Product
import com.ntc.shopree.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productRepository: ProductRepository) : ProductRepository {
    // TODO: Move to suspend fun cause it's a blocking call
    override fun getProducts(): List<Product> {
        return productRepository.getProducts()
    }
}