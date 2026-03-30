package com.ntc.shopree.core.model.repository

import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.dto.OrderResponse
import com.ntc.shopree.core.model.dto.OrderSummaryResponse

interface OrderRepository {
    suspend fun placeOrder(cartItems: List<CartItem>): OrderResponse
    suspend fun getOrder(id: String): OrderResponse
    suspend fun getOrders(): List<OrderSummaryResponse>
}
