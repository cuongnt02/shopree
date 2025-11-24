package com.ntc.shopree.domain.models

data class Category(
    val id: String,
    val name: String,
    val slug: String,
    val parent: Category? = null
)
