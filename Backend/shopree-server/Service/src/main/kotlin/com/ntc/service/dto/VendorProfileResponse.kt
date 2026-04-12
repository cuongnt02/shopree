package com.ntc.service.dto

import com.ntc.domain.model.Vendor
import java.util.UUID

data class VendorProfileResponse(
    val id: UUID?,
    val vendorName: String,
    val slug: String,
    val description: String?,
    val status: String,
    val address: Map<String, Any>?,
    val pickupAvailable: Boolean,
    val localDeliveryRadiusKm: Int
)

fun Vendor.toVendorProfileResponse() = VendorProfileResponse(
    id = id,
    vendorName = vendorName,
    slug = slug,
    description = description,
    status = status.name,
    address = address,
    pickupAvailable = pickupAvailable,
    localDeliveryRadiusKm = localDeliveryRadiusKm
)
