package com.ntc.shopree.core.network.service.impl

import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.network.dto.ProductResponse
import com.ntc.shopree.core.network.service.ProductService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
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
        return client.get {
            url("$baseUrl/api/v1/product/${slug}")
        }.body()
    }
}


