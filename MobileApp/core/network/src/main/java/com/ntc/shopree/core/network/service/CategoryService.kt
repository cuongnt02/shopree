package com.ntc.shopree.core.network.service

import com.ntc.shopree.core.network.dto.CategoryResponse

interface CategoryService {
    suspend fun getCategories(): List<CategoryResponse>
}

