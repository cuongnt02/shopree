package com.ntc.shopree.core.network.repository

import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.dto.OrderItemRequest
import com.ntc.shopree.core.model.dto.OrderResponse
import com.ntc.shopree.core.model.dto.OrderSummaryResponse
import com.ntc.shopree.core.model.dto.PlaceOrderRequest
import com.ntc.shopree.core.model.repository.OrderRepository
import com.ntc.shopree.core.network.service.OrderService
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderService: OrderService
) : OrderRepository {

    override suspend fun placeOrder(cartItems: List<CartItem>): OrderResponse {
        val request = PlaceOrderRequest(
            items = cartItems.map {
                OrderItemRequest(productSlug = it.productSlug, quantity = it.quantity)
            }
        )
        return orderService.placeOrder(request)
    }

    override suspend fun getOrder(id: String): OrderResponse = orderService.getOrder(id)

    override suspend fun getOrders(): List<OrderSummaryResponse> = orderService.getOrders()
}
