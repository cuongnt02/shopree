package com.ntc.service

import com.ntc.service.dto.OrderResponse
import com.ntc.service.dto.OrderSummaryResponse
import com.ntc.service.dto.PlaceOrderRequest
import java.util.UUID

interface OrderService {
    fun placeOrder(userId: UUID, request: PlaceOrderRequest): OrderResponse
    fun getOrders(userId: UUID): List<OrderSummaryResponse>
    fun getOrder(userId: UUID, orderId: UUID): OrderResponse
}