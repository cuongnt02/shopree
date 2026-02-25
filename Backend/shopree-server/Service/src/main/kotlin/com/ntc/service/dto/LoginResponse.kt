package com.ntc.service.dto

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Long,
    val userId: String,
    val role: String
)
