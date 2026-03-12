package com.ntc.shopree.feature.cart.domain

import com.ntc.shopree.core.database.CartRepository
import com.ntc.shopree.core.model.CartItem
import javax.inject.Inject

class DecrementCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(item: CartItem): Result<Int> {
        try {
            if (item.quantity <= 1) {
                cartRepository.removeItem(item)
                return Result.success(item.quantity)
            }
            cartRepository.decrementQuantity(item)
            val updatedItem = cartRepository.getItem(item.productSlug, item.vendorName, item.vendorName)
            return Result.success(updatedItem!!.quantity)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}