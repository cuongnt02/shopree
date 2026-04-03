package com.ntc.shopree.feature.profile.domain

import com.ntc.shopree.core.model.dto.OrderSummaryResponse
import com.ntc.shopree.core.model.repository.OrderRepository
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(): Result<List<OrderSummaryResponse>> {
        return try {
            Result.success(orderRepository.getOrders())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
