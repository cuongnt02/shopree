package com.ntc.shopree.feature.cart.domain

import com.ntc.shopree.core.ui.utils.SnackbarController
import com.ntc.shopree.core.ui.utils.SnackbarEvent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class ClearCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            cartRepository.clear()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}