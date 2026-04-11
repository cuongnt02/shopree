package com.ntc.service.dto

import com.ntc.domain.model.Product
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
    val startingPriceCents: Long?
)

fun Product.toVendorProductResponse(): VendorProductResponse {
    return VendorProductResponse(
        id = id,
        title = title,
        slug = slug,
        status = status.name,
        category = category?.name,
        mainImage = mainImage,
        pickupAvailable = pickupAvailable,
        startingPriceCents = variants.firstOrNull()?.priceCents
    )
}
