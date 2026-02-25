package com.ntc.data

import com.ntc.domain.model.RefreshToken
import com.ntc.domain.model.User
import org.springframework.data.repository.CrudRepository
import java.time.Instant
import java.util.UUID

interface RefreshTokenRepository : CrudRepository<RefreshToken, UUID> {
    fun findByTokenAndRevokedFalseAndExpiresAtAfter(
        token: String, now: Instant
    ): RefreshToken?

    fun findAllByUser(user: User): List<RefreshToken>

    fun deleteAllByUser(user: User)

    fun deleteAllByExpiresAtBefore(expiryDate: Instant)
}