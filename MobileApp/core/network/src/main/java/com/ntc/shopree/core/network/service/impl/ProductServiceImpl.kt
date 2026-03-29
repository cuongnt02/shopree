package com.ntc.shopree.core.network.service.impl

import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.network.dto.ProductResponse
import com.ntc.shopree.core.network.service.ProductService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import javax.inject.Inject
import javax.inject.Named

class ProductServiceImpl @Inject constructor(
    private val client: HttpClient,
    @Named("baseUrl") val baseUrl: String
) : ProductService {
    override suspend fun getProducts(): List<ProductResponse> {
        return client.get {
            url(urlString = "$baseUrl/api/v1/products")
        }.body()
    }

    override suspend fun getProductsByName(name: String): List<ProductResponse> {
        return client.get {
            url("$baseUrl/api/v1/products?name=$name")
        }.body()
    }

    override suspend fun getProduct(slug: String): ProductResponse {
        val response: HttpResponse = client.get {
            url("$baseUrl/api/v1/product/${slug}")
        }
        if (!response.status.isSuccess()) {
            throw Exception("Product not found (HTTP ${response.status.value}): $slug")
        }
        return response.body()
    }

    override suspend fun getProductsByCategory(categorySlug: String): List<ProductResponse> {
        val response: HttpResponse = client.get {
            url("$baseUrl/api/v1/products?category=$categorySlug")
        }
        if (!response.status.isSuccess()) {
            throw Exception("Failed to load products for category: $categorySlug (HTTP ${response.status.value})")
        }
        return response.body()
    }
}


