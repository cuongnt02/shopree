package com.ntc.service.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive


data class UpdateProductRequest(
    @field:NotBlank val title: String,
    @field:NotBlank val slug: String,
    val description: String?,
    val categorySlug: String?,
    val mainImage: String?,
    val status: String = "DRAFT",
    val pickupAvailable: Boolean = false,
    @field:Positive val priceCents: Long
)