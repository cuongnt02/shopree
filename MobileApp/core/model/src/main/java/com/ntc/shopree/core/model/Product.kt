package com.ntc.shopree.core.model

import kotlinx.serialization.Serializable

// TECHDEBT: Add price to Product model
// TECHDEBT: Add vendorName to Product model
@Serializable
data class Product (
    val id: String,
    val title: String,
    val slug: String,
    val vendorName: String,
    val description: String,
    val price: Double,
    val mainImage: String
)

