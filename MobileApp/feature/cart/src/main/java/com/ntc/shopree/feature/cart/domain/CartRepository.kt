package com.ntc.shopree.feature.cart.domain

import com.ntc.shopree.core.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun observeCart(): Flow<List<CartItem>>

    fun observeTotalQuantity(): Flow<Int>

    fun observeTotalPrice(): Flow<Double>
    suspend fun addItem(item: CartItem)
    suspend fun getItem(slug: String, vendor: String): CartItem?
    suspend fun incrementQuantity(item: CartItem)
    suspend fun decrementQuantity(item: CartItem)
    suspend fun removeItem(item: CartItem)
    suspend fun clear()
}
