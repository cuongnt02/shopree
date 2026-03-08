package com.ntc.shopree.core.network.domain

import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.model.repository.ProductRepository
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
