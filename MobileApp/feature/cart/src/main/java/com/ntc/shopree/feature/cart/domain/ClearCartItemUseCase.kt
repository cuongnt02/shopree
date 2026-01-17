package com.ntc.shopree.feature.cart.domain

import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class ClearCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        val cart = cartRepository.observeCart().toList()
        return try {
            if (cart.isEmpty()) return Result.success(Unit)
            cartRepository.clear()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}