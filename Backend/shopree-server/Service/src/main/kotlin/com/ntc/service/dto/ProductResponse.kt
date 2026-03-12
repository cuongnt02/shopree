package com.ntc.service.dto

import com.ntc.domain.model.Product
import com.ntc.domain.model.ProductVariant
import java.util.UUID

data class ProductResponse(
    val id: UUID?,
    val title: String,
    val slug: String,
    val description: String?,
    val mainImage: String?,
    val variants: List<ProductVariantResponse> = emptyList()
)

data class ProductVariantResponse(
    val id: UUID?,
    val title: String?,
    val sku: String?,
    val priceCents: Long,
    val compareAtCents: Long?,
    val inventoryCount: Int
)

fun Product.toProductResponse(): ProductResponse {
    return ProductResponse(
        id = this.id,
        title = this.title,
        slug = this.slug,
        description = this.description,
        mainImage = this.mainImage,
        variants = this.variants.map { it.toProductVariantResponse() }
    )
}

fun ProductVariant.toProductVariantResponse(): ProductVariantResponse {
    return ProductVariantResponse(
        id = this.id,
        title = this.title,
        sku = this.sku,
        priceCents = this.priceCents,
        compareAtCents = this.compareAtCents,
        inventoryCount = this.inventoryCount
    )
}


