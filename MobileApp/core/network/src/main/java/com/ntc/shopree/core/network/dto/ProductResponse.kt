package com.ntc.shopree.core.network.dto

import com.ntc.shopree.core.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: String,
    val title: String,
    val slug: String,
    val description: String?,
    val mainImage: String?,
)

fun ProductResponse.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        slug = slug,
        description = description ?: "",
        mainImage = mainImage ?: "",
        price = 0.0,
        vendorName = ""
    )
}
