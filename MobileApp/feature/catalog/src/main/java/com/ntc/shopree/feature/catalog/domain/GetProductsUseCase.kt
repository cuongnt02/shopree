package com.ntc.shopree.feature.catalog.domain

import com.ntc.shopree.core.model.Product
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
     suspend operator fun invoke(): Result<List<Product>> {
        return try {
            val products = productRepository.getProducts()
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}



