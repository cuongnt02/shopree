package com.ntc.shopree.feature.catalog.mappers

import com.ntc.shopree.core.model.CartItem
import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.model.ProductVariant

fun Product.toCartItem(variant: ProductVariant): CartItem {
    return CartItem(
        productSlug = slug,
        vendorName = vendorName,
        variantId = variant.id,
        variantName = variant.title ?: "Default",
        productName = title,
        price = variant.price,
        mainImage = mainImage,
        quantity = 1
    )
}
