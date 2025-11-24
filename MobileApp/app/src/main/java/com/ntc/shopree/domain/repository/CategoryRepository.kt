package com.ntc.shopree.domain.repository

import com.ntc.shopree.domain.models.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}