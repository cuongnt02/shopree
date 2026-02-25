package com.ntc.shopree.feature.cart.data

import com.ntc.shopree.core.database.CartDao
import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.feature.cart.domain.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {
    override fun observeCart(): Flow<List<CartItem>> = cartDao.observeCart().map { cartItems ->
        cartItems.map { it.toCartItem() }
    }

    override fun observeTotalQuantity(): Flow<Int> {
        return cartDao.observeTotalQuantity()
    }

    override fun observeTotalPrice(): Flow<Double> {
        return cartDao.observeTotalPrice()
    }

    override suspend fun addItem(item: CartItem) = cartDao.upsert(item.toCartItemEntity())

    override suspend fun getItem(slug: String, vendor: String): CartItem? {
        return cartDao.getItem(slug, vendor)?.toCartItem()
    }

    override suspend fun incrementQuantity(item: CartItem) =
        cartDao.incrementQuantity(item.productSlug, item.vendorName)


    override suspend fun decrementQuantity(item: CartItem) =
        cartDao.decrementQuantity(item.productSlug, item.vendorName)


    override suspend fun removeItem(item: CartItem) =
        cartDao.remove(item.productSlug, item.vendorName)

    override suspend fun clear() = cartDao.clear()
}