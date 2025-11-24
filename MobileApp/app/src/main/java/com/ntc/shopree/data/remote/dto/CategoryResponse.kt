package com.ntc.shopree.data.remote.dto

import com.ntc.shopree.domain.models.Category
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