package com.ntc.shopree.core.database

import com.ntc.shopree.core.model.CartItem
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

    override suspend fun getItem(slug: String, vendor: String, variantId: String): CartItem? {
        return cartDao.getItem(slug, vendor, variantId)?.toCartItem()
    }

    override suspend fun incrementQuantity(item: CartItem) =
        cartDao.incrementQuantity(item.productSlug, item.vendorName, item.variantId)


    override suspend fun decrementQuantity(item: CartItem) =
        cartDao.decrementQuantity(item.productSlug, item.vendorName, item.variantId)


    override suspend fun removeItem(item: CartItem) =
        cartDao.remove(item.productSlug, item.vendorName, item.variantId)

    override suspend fun clear() = cartDao.clear()
}