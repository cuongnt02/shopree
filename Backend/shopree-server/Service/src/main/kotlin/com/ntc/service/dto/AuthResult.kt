package com.ntc.service.dto

sealed class AuthResult {
    data class Success(
        val accessToken: String,
        val expiresAt: Long,
        val userId: String,
        val role: String
    ): AuthResult()
    data class Error(val message: String): AuthResult()
}