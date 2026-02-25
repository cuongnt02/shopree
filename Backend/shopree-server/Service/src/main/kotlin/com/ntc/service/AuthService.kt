package com.ntc.service

import com.ntc.service.dto.LoginResponse
import com.ntc.service.dto.RefreshTokenResponse

interface AuthService {
    fun login(username: String, password: String): LoginResponse
    fun logout(refreshToken: String)
    fun refreshAccessToken(refreshToken: String): RefreshTokenResponse
}