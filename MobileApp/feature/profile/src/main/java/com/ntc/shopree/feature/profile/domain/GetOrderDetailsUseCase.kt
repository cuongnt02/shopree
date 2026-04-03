package com.ntc.shopree.feature.profile.domain

import com.ntc.shopree.core.model.dto.OrderResponse
import com.ntc.shopree.core.model.repository.OrderRepository
import javax.inject.Inject

class GetOrderDetailsUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(id: String): Result<OrderResponse> {
        return try {
            Result.success(orderRepository.getOrder(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
