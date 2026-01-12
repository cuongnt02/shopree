package com.ntc.shopree.feature.catalog.domain

import com.ntc.shopree.core.model.Product
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(query: String): Result<List<Product>> {
        return try {
            if (query.isBlank()) {
                val allProducts = productRepository.getProducts()
                return Result.success(allProducts)
            }
            val products = productRepository.findProductsByName(query)

            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}