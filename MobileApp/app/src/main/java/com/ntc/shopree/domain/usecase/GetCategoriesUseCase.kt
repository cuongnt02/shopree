package com.ntc.shopree.domain.usecase

import com.ntc.shopree.domain.models.Category
import com.ntc.shopree.domain.repository.CategoryRepository

class GetCategoriesUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(): Result<List<Category>> {
        return try {
            val categories = categoryRepository.getCategories()
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}