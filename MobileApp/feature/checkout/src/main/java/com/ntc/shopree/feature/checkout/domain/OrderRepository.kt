package com.ntc.shopree.feature.checkout.domain

import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.dto.OrderResponse

interface OrderRepository {
    suspend fun placeOrder(cartItems: List<CartItem>): Result<OrderResponse>
}