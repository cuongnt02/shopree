package com.ntc.shopree.feature.checkout.data

import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.dto.OrderItemRequest
import com.ntc.shopree.core.model.dto.OrderResponse
import com.ntc.shopree.core.model.dto.PlaceOrderRequest
import com.ntc.shopree.core.network.service.OrderService
import com.ntc.shopree.feature.checkout.domain.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderService: OrderService) :
    OrderRepository {
    override suspend fun placeOrder(cartItems: List<CartItem>): Result<OrderResponse> {
        return try {
            val request = PlaceOrderRequest(
                items = cartItems.map {
                    OrderItemRequest(
                        productSlug = it.productSlug, quantity = it.quantity
                    )
                })
            Result.success(orderService.placeOrder(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}