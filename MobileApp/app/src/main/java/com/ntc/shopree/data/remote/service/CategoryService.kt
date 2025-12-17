package com.ntc.shopree.data.remote.service

import com.ntc.shopree.data.remote.dto.CategoryResponse

interface CategoryService {
    suspend fun getCategories(): List<CategoryResponse>
}