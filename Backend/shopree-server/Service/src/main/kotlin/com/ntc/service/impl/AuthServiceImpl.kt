package com.ntc.service.impl

import com.ntc.domain.exception.AuthenticationException
import com.ntc.security.JwtTokenProvider
import com.ntc.service.AuthService
import com.ntc.domain.model.User
import com.ntc.service.RefreshTokenService
import com.ntc.service.dto.LoginResponse
import com.ntc.service.dto.RefreshTokenResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(private val authenticationManager: AuthenticationManager, private val tokenProvider: JwtTokenProvider, private val refreshTokenService: RefreshTokenService):
    AuthService {
    override fun login(username: String, password: String): LoginResponse {
        try {
            val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))

            SecurityContextHolder.getContext().authentication = auth
            val user = auth.principal as User
            val jwt = tokenProvider.generateToken(auth.principal as User)
            val payload = tokenProvider.getJwtPayload(jwt)

            val refreshToken = refreshTokenService.createRefreshToken(user)

            return LoginResponse (
                accessToken = jwt,
                refreshToken = refreshToken.token,
                expiresAt = payload.expiration.time,
                userId = payload.subject,
                role = user.role.toString(),
            )
        } catch (e: Exception) {
            throw AuthenticationException("Authentication failed")
        }

    }

    override fun refreshAccessToken(refreshToken: String): RefreshTokenResponse {
        val user = refreshTokenService.validateAndGetUser(refreshToken) ?: throw AuthenticationException("Invalid or expired refresh token")

        val newAccessToken = tokenProvider.generateToken(user)
        val payload = tokenProvider.getJwtPayload(newAccessToken)

        // Rotates refresh token to prevent token replay attacks
        refreshTokenService.revokeToken(refreshToken)
        val newRefreshToken = refreshTokenService.createRefreshToken(user)

        return RefreshTokenResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken.token,
            expiresAt = payload.expiration.time
        )
    }

    override fun logout(refreshToken: String) {
        refreshTokenService.revokeToken(refreshToken)
    }
}
