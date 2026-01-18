package com.ntc.shopree.feature.cart.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTotalPriceUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Result<Flow<Double>> {
        return try {
            Result.success(cartRepository.observeTotalPrice())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
