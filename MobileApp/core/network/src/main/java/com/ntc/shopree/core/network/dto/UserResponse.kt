package com.ntc.shopree.core.network.dto

import com.ntc.shopree.core.model.User
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class UserResponse(
    val id: String,
    val email: String,
    val phone: String,
    val name: String,
    val metadata: Map<String, JsonElement>,
    val role: String,
    val verified: Boolean,
    val createdAt: Long,
)

fun UserResponse.toUser(): User {
    return User(
        id = id,
        email = email,
        phone = phone,
        name = name,
        avatar = metadata["avatar"]?.jsonPrimitive?.contentOrNull ?: "",
        role = role,
        verified = verified,
        createdAt = createdAt,
    )
}

