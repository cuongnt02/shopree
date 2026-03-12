package com.ntc.shopree.core.network.repository

import com.ntc.shopree.core.model.Category
import com.ntc.shopree.core.model.repository.CategoryRepository
import com.ntc.shopree.core.network.dto.toCategory
import com.ntc.shopree.core.network.service.CategoryService
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService
) : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return categoryService.getCategories().map { it.toCategory() }
    }
}
