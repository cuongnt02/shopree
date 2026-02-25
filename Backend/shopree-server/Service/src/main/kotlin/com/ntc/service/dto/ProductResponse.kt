package com.ntc.service.dto

import com.ntc.domain.model.Product
import java.util.UUID

data class ProductResponse(
    val id: UUID?,
    val title: String,
    val slug: String,
    val description: String?,
    val mainImage: String?,
)

fun Product.toProductResponse(): ProductResponse {
    return ProductResponse(
        id = this.id,
        title = this.title,
        slug = this.slug,
        description = this.description,
        mainImage = this.mainImage
    )
}


