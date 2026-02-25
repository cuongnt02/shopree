package com.ntc.service.impl

import com.ntc.data.CategoryRepository
import com.ntc.service.CategoryService
import com.ntc.domain.model.Category
import com.ntc.service.dto.CategoryResponse
import com.ntc.service.dto.toCategoryResponse
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(val categoryRepository: CategoryRepository) : CategoryService {
    override fun getCategories(): List<CategoryResponse> = categoryRepository.findAll().map {
        it.toCategoryResponse()
    }
    override fun getCategory(slug: String): CategoryResponse? = categoryRepository.findCategoryBySlug(slug)?.toCategoryResponse()
}