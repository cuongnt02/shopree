package com.ntc.shopree.core.model

import kotlinx.serialization.Serializable

// TODO: Map this to the actual data model
@Serializable
data class Product (
    val id: Int,
    val slug: String,
    val vendorName: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String
)

