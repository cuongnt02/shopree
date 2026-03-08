package com.ntc.shopree.feature.cart.domain

import com.ntc.shopree.core.database.CartRepository
import com.ntc.shopree.core.model.CartItem
import javax.inject.Inject

class UpdateCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(oldItem: CartItem, newItem: CartItem): Result<Unit> {
        return try {
            cartRepository.removeItem(oldItem)
            cartRepository.addItem(newItem)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
