package com.ntc.service

import com.ntc.domain.model.Category
import com.ntc.service.dto.CategoryResponse

interface CategoryService {
    fun getCategories(): List<CategoryResponse>
    fun getCategory(slug: String): CategoryResponse?
}