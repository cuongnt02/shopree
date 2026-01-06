package com.ntc.shopree.feature.auth.domain

import android.util.Log
import com.ntc.shopree.core.datastore.SessionStore
import com.ntc.shopree.core.model.Session
import com.ntc.shopree.feature.auth.data.remote.service.FirebaseService
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val firebaseService: FirebaseService,
    private val authRepository: AuthRepository,
    private val sessionStore: SessionStore
) {
    // TODO: Handle nullable session and map to a dedicated auth error state.
    // TODO: Handle gracefully wrong credential logins
    suspend operator fun invoke(email: String, password: String): Result<Session?> {
        var idToken = firebaseRepository.getTokenId()

        if (idToken == null) {
            val result = firebaseService.login(username = email, password = password)
            result.onSuccess {
                idToken = firebaseRepository.getTokenId()
            }.onFailure {
                return Result.failure(it)
            }
        }

        return try {
            val session = authRepository.getSession(
                email = email,
                password = password,
                firebaseToken = idToken!!
            )
            if (session != null) {
                sessionStore.saveSession(session.accessToken, session.refreshToken, session.expiresAt)
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



