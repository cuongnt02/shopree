package com.ntc.shopree.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val name: String,
    val slug: String,
    val parent: Category? = null
)

