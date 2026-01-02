package com.ntc.shopree.core.network.dto

import com.ntc.shopree.core.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val id: String,
    val name: String,
    val slug: String,
    val parent: CategoryResponse? = null
)

fun CategoryResponse.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        slug = slug,
        parent = parent?.toCategory())
}
