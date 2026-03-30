package com.ntc.shopree.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val email: String,
    val phone: String,
    val name: String,
    val avatar: String,
    val role: String,
    val verified: Boolean = false,
    val createdAt: Long = 0L,
)

