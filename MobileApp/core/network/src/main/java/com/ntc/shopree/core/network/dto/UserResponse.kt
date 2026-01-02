package com.ntc.shopree.core.network.dto

import com.ntc.shopree.core.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val email: String,
    val phone: String,
    val name: String,
    val metadata: Map<String, String>,
    val role: String,
)

fun UserResponse.toUser(): User {
    return User(
        email = email,
        phone = phone,
        name = name,
        avatar = metadata["avatar"] ?: "",
        role = role,
    )
}

