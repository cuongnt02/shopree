package com.ntc.shopree.core.network.service.impl

import com.ntc.shopree.core.network.service.CategoryService
import com.ntc.shopree.core.network.dto.CategoryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject
import javax.inject.Named

class CategoryServiceImpl @Inject constructor(
    private val client: HttpClient,
    @Named("baseUrl") private val baseUrl: String
) : CategoryService {
    override suspend fun getCategories(): List<CategoryResponse> {
        return client.get {
            url("$baseUrl/api/v1/categories")
        }.body()
    }
}

