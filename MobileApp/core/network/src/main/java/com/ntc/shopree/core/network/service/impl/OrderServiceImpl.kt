package com.ntc.shopree.core.network.service.impl

import com.ntc.shopree.core.model.dto.OrderResponse
import com.ntc.shopree.core.model.dto.OrderSummaryResponse
import com.ntc.shopree.core.model.dto.PlaceOrderRequest
import com.ntc.shopree.core.network.service.OrderService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Named

class OrderServiceImpl @Inject constructor(
    private val client: HttpClient,
    @Named("baseUrl") private val baseUrl: String
): OrderService {
    override suspend fun placeOrder(request: PlaceOrderRequest): OrderResponse =
        client.post {
            url("$baseUrl/api/v1/orders")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    override suspend fun getOrder(id: String): OrderResponse = client.get { url("$baseUrl/api/v1/order/$id") }.body()

    override suspend fun getOrders(): List<OrderSummaryResponse> = client.get { url("$baseUrl/api/v1/orders") }.body()
}