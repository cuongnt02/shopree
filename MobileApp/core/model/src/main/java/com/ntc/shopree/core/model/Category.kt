package com.ntc.shopree.core.model

data class Category(
    val id: String,
    val name: String,
    val slug: String,
    val parent: Category? = null
)

