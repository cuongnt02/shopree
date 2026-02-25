package com.ntc.shopree.feature.cart.domain

import com.ntc.shopree.core.model.CartItem
import javax.inject.Inject

class IncrementCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(item: CartItem): Result<Int> {
        return try {
            cartRepository.incrementQuantity(item)
            val updatedItem: CartItem? = cartRepository.getItem(item.productSlug,  item.vendorName)
            Result.success(updatedItem!!.quantity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}