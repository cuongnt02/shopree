package com.ntc.shopree.domain.usecase

import com.ntc.shopree.domain.models.Product
import com.ntc.shopree.domain.repository.ProductRepository
class GetProductsUseCase(private val productRepository: ProductRepository) {
     suspend operator fun invoke(): Result<List<Product>> {
        return try {
            val products = productRepository.getProducts()
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
