package com.ntc.shopree.feature.cart.data

import com.ntc.shopree.core.database.CartItemEntity
import com.ntc.shopree.core.model.CartItem

fun CartItemEntity.toCartItem(): CartItem {
    return CartItem(
        productSlug = productSlug,
        vendorName = vendorName,
        productName = productName,
        quantity = quantity,
        price = price,
        mainImage = mainImage
    )
}

fun CartItem.toCartItemEntity(): CartItemEntity {
    return CartItemEntity(
        productSlug = productSlug,
        vendorName = vendorName,
        productName = productName,
        quantity = quantity,
        price = price,
        mainImage = mainImage
    )
}