package com.ntc.shopree.feature.cart.domain

import com.ntc.shopree.core.model.CartItem
import javax.inject.Inject

class RemoveCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(item: CartItem): Result<Unit> {
        return try {
            cartRepository.removeItem(item)
            Result.success()
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

}