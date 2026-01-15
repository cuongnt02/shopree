package com.ntc.shopree.feature.cart.domain

import com.ntc.shopree.core.model.CartItem
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(
        productSlug: String,
        vendorName: String,
        productName: String,
        price: Double,
        imageUrl: String,
        quantity: Int,
    ): Result<CartItem> {
        return try {
            val cartItem = CartItem(
                productSlug = productSlug,
                vendorName = vendorName,
                productName = productName,
                price = price,
                imageUrl = imageUrl,
                quantity = quantity
            )
            cartRepository.addItem(cartItem)
            Result.success(cartItem)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}