package com.ntc.shopree.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val phone: String,
    val name: String,
    val avatar: String,
    val role: String,
)

