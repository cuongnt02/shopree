package com.ntc.shopree.domain.repository.impl

import com.ntc.shopree.data.remote.CategoryService
import com.ntc.shopree.data.remote.dto.toCategory
import com.ntc.shopree.domain.models.Category
import com.ntc.shopree.domain.repository.CategoryRepository

class CategoryRepositoryImpl(private val categoryService: CategoryService): CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return categoryService.getCategories().map { it.toCategory() }
    }

}