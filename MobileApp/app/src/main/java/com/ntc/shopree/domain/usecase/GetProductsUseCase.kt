package com.ntc.shopree.domain.usecase

import com.ntc.shopree.domain.repository.ProductRepository
// TODO: Move to suspend fun cause it's a blocking call
class GetProductsUseCase(private val productRepository: ProductRepository) {
     operator fun invoke() {
        try {
            val products = productRepository.getProducts()
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
