package com.ntc.shopree.feature.cart.domain

import com.ntc.shopree.core.model.CartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Result<Flow<List<CartItem>>> {

        return try {
            cartRepository.observeCart()
            Result.success(cartRepository.observeCart())
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}