package com.ntc.shopree.feature.cart.domain

import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.feature.cart.data.toCartItemEntity
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
            val itemId = item.toCartItemEntity().id
            val updatedItem = cartRepository.getItem(itemId)
            return Result.success(updatedItem.quantity)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}