package com.ntc.shopree.core.network.service

import com.ntc.shopree.core.model.dto.OrderResponse
import com.ntc.shopree.core.model.dto.OrderSummaryResponse
import com.ntc.shopree.core.model.dto.PlaceOrderRequest

interface OrderService {
    suspend fun placeOrder(request: PlaceOrderRequest): OrderResponse
    suspend fun getOrder(id: String): OrderResponse
    suspend fun getOrders(): List<OrderSummaryResponse>
}