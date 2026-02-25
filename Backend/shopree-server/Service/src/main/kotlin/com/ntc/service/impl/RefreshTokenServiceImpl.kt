package com.ntc.service.impl

import com.ntc.data.RefreshTokenRepository
import com.ntc.domain.model.RefreshToken
import com.ntc.domain.model.User
import com.ntc.service.RefreshTokenService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class RefreshTokenServiceImpl (
    private val refreshTokenRepository: RefreshTokenRepository,
    @Value($$"${jwt.refresh-token-expiration-days:30}")
    private val refreshTokenExpirationDays: Long
): RefreshTokenService {
    @Transactional
    override fun createRefreshToken(
        user: User,
        deviceInfo: String?,
        ipAddress: String?
    ): RefreshToken {
        val token = UUID.randomUUID().toString()
        val expiresAt = Instant.now().plusSeconds(refreshTokenExpirationDays * 24 * 3600)

        val refreshToken = RefreshToken(
            token = token,
            user = user,
            expiresAt = expiresAt,
            deviceInfo = deviceInfo,
            ipAddress = ipAddress
        )
        return refreshTokenRepository.save(refreshToken)
    }

    override fun validateAndGetUser(token: String): User? {
        val refreshToken = refreshTokenRepository.findByTokenAndRevokedFalseAndExpiresAtAfter(token, Instant.now()) ?: return null
        return refreshToken.user
    }

    @Transactional
    override fun revokeToken(token: String) {
        refreshTokenRepository.findByTokenAndRevokedFalseAndExpiresAtAfter(token, Instant.now())
            ?.let {
                it.revoked = true
                refreshTokenRepository.save(it)
            }
    }

    @Transactional
    override fun revokeAllUserTokens(user: User) {
        refreshTokenRepository.deleteAllByUser(user)
    }
}