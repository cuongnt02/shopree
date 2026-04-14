package com.ntc.service

import com.ntc.service.dto.OrderResponse
import com.ntc.service.dto.OrderSummaryResponse
import com.ntc.service.dto.PlaceOrderRequest
import java.util.UUID

interface OrderService {
    fun placeOrder(userId: UUID, request: PlaceOrderRequest): OrderResponse
    fun getOrders(userId: UUID): List<OrderSummaryResponse>
    fun getOrder(userId: UUID, orderId: UUID): OrderResponse
    fun getOrdersByVendor(userId: UUID, status: String?): List<OrderSummaryResponse>
    fun updateOrderStatus(userId: UUID, orderId: UUID, newStatus: String)
    fun getVendorOrder(userId: UUID, orderId: UUID): OrderResponse
}