package com.ntc.shopree.feature.cart.domain

import com.ntc.shopree.core.model.CartItem
import javax.inject.Inject

class IncrementCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(item: CartItem): Result<Int> {
        return try {
            cartRepository.incrementQuantity(item)
            Result.success(item.quantity)
            } catch (e: Exception) {
            Result.failure(e)
        }
    }
}