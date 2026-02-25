package com.ntc.service

import com.ntc.domain.model.RefreshToken
import com.ntc.domain.model.User

interface RefreshTokenService {

    fun createRefreshToken(user: User, deviceInfo: String? = null, ipAddress: String? = null): RefreshToken
    fun validateAndGetUser(token: String): User?
    fun revokeToken(token: String)
    fun revokeAllUserTokens(user: User)
}