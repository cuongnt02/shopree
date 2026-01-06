package com.ntc.shopree.core.network.dto

import com.ntc.shopree.core.model.Session
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class LoginResponse(
    val accessToken: String,
    // TODO: Add refresh token field (backend also)
    @Transient val refreshToken: String = "",
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

