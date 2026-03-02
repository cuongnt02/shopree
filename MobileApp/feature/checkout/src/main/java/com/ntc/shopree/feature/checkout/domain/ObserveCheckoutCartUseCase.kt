package com.ntc.shopree.feature.checkout.domain

import com.ntc.shopree.core.database.CartRepository
import com.ntc.shopree.core.model.CartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ObserveCheckoutCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Result<Flow<List<CartItem>>> {

        return try {
            Result.success(cartRepository.observeCart())
        }
        catch (e: Exception) {
            Result.failure(e)
        }

    }
}
