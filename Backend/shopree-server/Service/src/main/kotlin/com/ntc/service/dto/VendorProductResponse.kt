package com.ntc.service.dto

import com.ntc.domain.model.Product
import com.ntc.domain.model.ProductImage
import com.ntc.domain.model.Vendor
import java.util.UUID

data class VendorProductResponse(
    val id: UUID?,
    val title: String,
    val slug: String,
    val status: String,
    val category: String?,
    val mainImage: String?,
    val pickupAvailable: Boolean,
    val startingPriceCents: Long?,
    val images: List<ProductImageResponse> = emptyList()
)

fun Product.toVendorProductResponse(images: List<ProductImage> = emptyList()): VendorProductResponse {
    return VendorProductResponse(
        id = id,
        title = title,
        slug = slug,
        status = status.name,
        category = category?.name,
        mainImage = mainImage,
        pickupAvailable = pickupAvailable,
        startingPriceCents = variants.firstOrNull()?.priceCents,
        images = images.map { it.toProductImageResponse() }
    )
}
