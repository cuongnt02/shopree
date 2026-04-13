package com.ntc.service.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive

data class VariantRequest(
    val title: String?,
    val sku: String?,
    @field:Positive val priceCents: Long,
    val compareAtCents: Long?,
    @field:Min(0) val inventoryCount: Int = 0
)