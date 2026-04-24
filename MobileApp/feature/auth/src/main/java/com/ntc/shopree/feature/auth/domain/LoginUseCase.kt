package com.ntc.shopree.feature.auth.domain

import android.util.Log
import com.ntc.shopree.core.datastore.SessionStore
import com.ntc.shopree.core.model.Session
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val authRepository: AuthRepository,
    private val sessionStore: SessionStore
) {
    // TODO: Handle nullable session and map to a dedicated auth error state.
    // TODO: Handle gracefully wrong credential logins
    suspend operator fun invoke(
        identifier: String,
        password: String,
        rememberMe: Boolean
    ): Result<Session?> {
        val isPhone = identifier.startsWith("+") ||
                identifier.all { it.isDigit() || it == '+' || it == ' ' }

        if (!isPhone) {
            var idToken = firebaseRepository.getTokenId()
            if (idToken == null) {
                val result = firebaseRepository.login(identifier, password)
                result.onFailure { return Result.failure(it) }
            }
        }

        return try {
            val session = authRepository.getSession(
                identifier = identifier, password = password, firebaseToken = ""
            )
            // WARN: Not quite right, should be checking if the current session is valid or not somewhere else
            if (session != null) {
                sessionStore.clearSession()
                sessionStore.saveSession(
                    session.accessToken, session.refreshToken, session.expiresAt, rememberMe
                )
                Log.d("LoginUseCase", "Session saved: $session")
                Result.success(session)
            }
            // TODO: Handle null session, show more descriptive exception message
            else return Result.failure(Exception("Session is null"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}



