package com.ntc.shopree.core.network.dto

import com.ntc.shopree.core.model.Session
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String = "",
    val expiresAt: Long,
    val userId: String,
)

fun LoginResponse.toSession(): Session {
    return Session(
        accessToken = accessToken,
        refreshToken = refreshToken,
        expiresAt = expiresAt,
        userId = userId,
    )
}

