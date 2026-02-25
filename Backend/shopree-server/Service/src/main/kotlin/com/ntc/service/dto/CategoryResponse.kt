package com.ntc.service.dto

import com.ntc.domain.model.Category
import java.util.UUID

data class CategoryResponse(
    var id: String,
    var name: String,
    var slug: String,
    var parentId: String,
)

fun Category.toCategoryResponse(): CategoryResponse {
    return CategoryResponse(
        id = this.id.toString(),
        name = this.name,
        slug = this.slug,
        parentId = this.parent?.id.toString()
    )
}

