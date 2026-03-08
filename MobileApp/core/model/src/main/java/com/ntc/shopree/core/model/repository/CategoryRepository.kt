package com.ntc.shopree.core.model.repository

import com.ntc.shopree.core.model.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}
