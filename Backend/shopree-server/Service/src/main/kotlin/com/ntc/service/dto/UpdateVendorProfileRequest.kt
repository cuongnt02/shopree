package com.ntc.service.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class UpdateVendorProfileRequest(
    @field:NotBlank val vendorName: String,
    val description: String?,
    val address: Map<String, Any>?,
    val pickupAvailable: Boolean,
    @field:Positive val localDeliveryRadiusKm: Int
)
