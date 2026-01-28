package com.ntc.service.impl

import com.ntc.data.CategoryRepository
import com.ntc.service.CategoryService
import com.ntc.shopree.model.Category
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(val categoryRepository: CategoryRepository) : CategoryService {
    override fun getCategories(): List<Category> = categoryRepository.findAll().toList()
    override fun getCategory(slug: String): Category? = categoryRepository.findCategoryBySlug(slug)
}