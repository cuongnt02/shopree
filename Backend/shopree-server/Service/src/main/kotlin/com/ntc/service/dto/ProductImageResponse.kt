package com.ntc.service.dto

import com.ntc.domain.model.ProductImage

data class ProductImageResponse(
    val id: String,
    val url: String,
    val altText: String?,
    val position: Int
)

fun ProductImage.toProductImageResponse() = ProductImageResponse(
    id = this.id.toString(),
    url = this.url,
    altText = this.altText,
    position = this.position
)
