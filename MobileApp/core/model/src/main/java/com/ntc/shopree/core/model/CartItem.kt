package com.ntc.shopree.core.model

data class CartItem(
    val productSlug: String,
    val vendorName: String,
    val productName: String,
    val quantity: Int,
    val price: Double,
    val imageUrl: String
)