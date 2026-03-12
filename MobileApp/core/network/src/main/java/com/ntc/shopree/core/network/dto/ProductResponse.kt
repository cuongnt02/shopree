package com.ntc.shopree.core.network.dto

import com.ntc.shopree.core.model.Product
import com.ntc.shopree.core.model.ProductVariant
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val id: String,
    val title: String,
    val slug: String,
    val description: String?,
    val mainImage: String?,
    val variants: List<ProductVariantResponse> = emptyList()
)

@Serializable
data class ProductVariantResponse(
    val id: String,
    val title: String?,
    val sku: String?,
    val priceCents: Long,
    val compareAtCents: Long?,
    val inventoryCount: Int
)

fun ProductResponse.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        slug = slug,
        description = description ?: "",
        mainImage = mainImage ?: "",
        vendorName = "", // To be populated later
        variants = variants.map { it.toProductVariant() }
    )
}

fun ProductVariantResponse.toProductVariant(): ProductVariant {
    return ProductVariant(
        id = id,
        title = title,
        sku = sku,
        priceCents = priceCents,
        compareAtCents = compareAtCents,
        inventoryCount = inventoryCount
    )
}
