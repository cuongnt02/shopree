package com.ntc.shopree.feature.cart.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCartQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(): Result<Flow<Int>> {
        return try {
            val quantity = cartRepository.observeTotalQuantity()
            Result.success(quantity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}