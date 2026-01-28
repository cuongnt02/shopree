package com.ntc.shopree.feature.catalog.mappers

import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.Product

fun Product.toCartItem(): CartItem {
    return CartItem(
        productSlug = slug,
        vendorName = vendorName,
        productName = name,
        price = price,
        imageUrl = imageUrl,
        quantity = 1
    )
}
