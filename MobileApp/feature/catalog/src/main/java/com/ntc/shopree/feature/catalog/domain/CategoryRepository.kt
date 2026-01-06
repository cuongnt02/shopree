package com.ntc.shopree.feature.catalog.domain

import com.ntc.shopree.core.model.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}

