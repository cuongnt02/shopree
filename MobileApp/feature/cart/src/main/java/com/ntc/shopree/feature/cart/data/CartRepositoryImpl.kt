package com.ntc.shopree.feature.cart.data

import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.feature.cart.domain.CartRepository
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl : CartRepository {
    override fun observeCart(): Flow<List<CartItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun addItem(item: CartItem) {
        TODO("Not yet implemented")
    }

    override suspend fun incrementQuantity(item: CartItem) {
        TODO("Not yet implemented")
    }

    override suspend fun decrementQuantity(item: CartItem) {
        TODO("Not yet implemented")
    }

    override suspend fun removeItem(item: CartItem) {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }
}