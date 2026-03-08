package com.ntc.shopree.core.database

import com.ntc.shopree.core.model.CartItem

fun CartItemEntity.toCartItem(): CartItem {
    return CartItem(
        productSlug = productSlug,
        vendorName = vendorName,
        variantId = variantId,
        variantName = variantName,
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
        variantId = variantId,
        variantName = variantName,
        productName = productName,
        quantity = quantity,
        price = price,
        mainImage = mainImage
    )
}