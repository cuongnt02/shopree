package com.ntc.shopree.feature.catalog.domain

import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.model.repository.ProductRepository
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(query: String): Result<List<Product>> {
        return try {
            val products = productRepository.findProductsByName(query)
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
