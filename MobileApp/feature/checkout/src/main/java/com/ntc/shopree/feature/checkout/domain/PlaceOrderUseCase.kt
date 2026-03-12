package com.ntc.shopree.feature.checkout.domain

import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.dto.OrderResponse
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(items: List<CartItem>): Result<OrderResponse> = orderRepository.placeOrder(items)
}
