package com.ntc.shopree.feature.catalog.domain

import com.ntc.shopree.core.model.Category
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,

) {
    suspend operator fun invoke(): Result<List<Category>> {
        return try {
            val categories = categoryRepository.getCategories()
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


