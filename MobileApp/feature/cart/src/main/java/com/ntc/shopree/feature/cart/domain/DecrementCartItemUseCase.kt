package com.ntc.shopree.feature.cart.domain

import com.ntc.shopree.core.model.CartItem
import javax.inject.Inject

class DecrementCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(item: CartItem): Result<Int> {
        return try {
            if (item.quantity <= 1) {
                cartRepository.removeItem(item)
                Result.success(item.quantity)
            }
            cartRepository.decrementQuantity(item)
            Result.success(item.quantity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}