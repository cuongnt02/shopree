package com.ntc.shopree.feature.catalog.domain

import com.ntc.shopree.core.model.Product
import javax.inject.Inject

class GetSingleProductUseCase @Inject constructor (
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(slug: String): Result<Product> {
        return try {
            val product = productRepository.getProduct(slug)
            Result.success(product)
            } catch (e: Exception) {
            Result.failure(e)
        }
    }
}