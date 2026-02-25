package com.ntc.service.dto

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Long
)