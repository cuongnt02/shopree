package com.ntc.domain.dto

import java.util.UUID

data class ProductResponse(
    val id: UUID,
    val title: String,
    val slug: String,
    val description: String,
    val mainImage: String,
    val price: Double,
)