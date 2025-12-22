package com.ntc.api.payload

import com.ntc.api.payload.error.UnexpectedErrorResponse
import com.ntc.service.dto.AuthResult

data class LoginResponse(val accessToken: String, val expiresAt: Long, val userId: String, val role: String): ResponseBase

fun AuthResult.toLoginResponse() = when (this) {
    is AuthResult.Success -> {
        LoginResponse(accessToken, expiresAt, userId, role)
    }
    is AuthResult.Error -> UnexpectedErrorResponse(message)
}