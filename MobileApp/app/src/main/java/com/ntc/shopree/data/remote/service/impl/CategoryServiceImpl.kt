package com.ntc.shopree.data.remote.service.impl

import com.ntc.shopree.ShopreeApplication
import com.ntc.shopree.data.remote.service.CategoryService
import com.ntc.shopree.data.remote.dto.CategoryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class CategoryServiceImpl(
    private val client: HttpClient
): CategoryService {
    override suspend fun getCategories(): List<CategoryResponse> {
        return client.get {
            url("${ShopreeApplication.SHOPREE_BASE_URL}/api/v1/categories")
        }.body()
    }
}