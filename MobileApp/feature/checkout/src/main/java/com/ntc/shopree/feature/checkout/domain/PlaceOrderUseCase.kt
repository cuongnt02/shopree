package com.ntc.shopree.feature.checkout.domain

import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.dto.OrderResponse
import com.ntc.shopree.core.model.repository.OrderRepository
import javax.inject.Inject

class PlaceOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(items: List<CartItem>): Result<OrderResponse> {
        return try {
            Result.success(orderRepository.placeOrder(items))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
