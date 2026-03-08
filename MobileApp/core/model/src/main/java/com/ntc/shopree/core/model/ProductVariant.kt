package com.ntc.shopree.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductVariant(
    val id: String,
    val title: String?,
    val sku: String?,
    val priceCents: Long,
    val compareAtCents: Long?,
    val inventoryCount: Int
) {
    val price: Double get() = priceCents / 100.0
}
