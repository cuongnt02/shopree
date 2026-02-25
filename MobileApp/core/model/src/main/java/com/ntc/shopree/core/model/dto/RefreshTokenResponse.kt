package com.ntc.shopree.core.model.dto

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Long,
    val userId: String,
    val role: String
)