package com.ntc.shopree.feature.cart.data

import com.ntc.shopree.core.database.CartItemEntity
import com.ntc.shopree.core.model.CartItem

fun CartItemEntity.toCartItem(): CartItem {
    val (productSlug, vendorName) = id.split("_")
    return CartItem(
        productSlug = productSlug,
        vendorName = vendorName,
        productName = productName,
        quantity = quantity,
        price = price,
        imageUrl = imageUrl
    )
}

fun CartItem.toCartItemEntity(): CartItemEntity {
    return CartItemEntity(
        id = "${productSlug}_${vendorName}",
        productSlug = productSlug,
        vendorName = vendorName,
        productName = productName,
        quantity = quantity,
        price = price,
        imageUrl = imageUrl
    )
}

fun itemId(item: CartItem): String {
    return item.toCartItemEntity().id
}