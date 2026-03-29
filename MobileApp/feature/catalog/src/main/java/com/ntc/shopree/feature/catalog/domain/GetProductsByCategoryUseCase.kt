package com.ntc.shopree.feature.catalog.domain

import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.model.repository.ProductRepository
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(categorySlug: String): Result<List<Product>> {
        return try {
            Result.success(productRepository.getProductsByCategory(categorySlug))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
