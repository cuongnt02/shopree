package com.ntc.shopree.feature.cart.domain

import com.ntc.shopree.core.model.CartItem
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(
        cartItem: CartItem
    ): Result<CartItem> {
        return try {
            cartRepository.addItem(cartItem)
            Result.success(cartItem)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}