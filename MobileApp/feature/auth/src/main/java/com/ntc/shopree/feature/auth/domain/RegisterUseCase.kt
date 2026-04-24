package com.ntc.shopree.feature.auth.domain

import com.ntc.shopree.core.datastore.SessionStore
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionStore: SessionStore
) {
    suspend operator fun invoke(name: String, email: String, phone: String, password: String): Result<Unit> = try {
        val session = authRepository.register(name, email, phone, password)
        sessionStore.saveSession(session.accessToken, session.refreshToken, session.expiresAt, true)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}