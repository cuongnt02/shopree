package com.ntc.service

import com.ntc.shopree.model.Category

interface CategoryService {
    fun getCategories(): List<Category>
    fun getCategory(slug: String): Category?
}