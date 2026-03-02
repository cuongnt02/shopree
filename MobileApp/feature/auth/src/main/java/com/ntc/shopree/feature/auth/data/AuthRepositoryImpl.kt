package com.ntc.shopree.feature.auth.data

import com.ntc.shopree.core.datastore.SessionStore
import com.ntc.shopree.core.model.Session
import com.ntc.shopree.core.network.dto.toSession
import com.ntc.shopree.core.network.service.AuthService
import com.ntc.shopree.feature.auth.domain.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val sessionStore: SessionStore
) : AuthRepository {
    override suspend fun getSession(email: String, password: String, firebaseToken: String): Session {
        return authService.loginWithEmailAndPassword(email, password).toSession()
    }

    override suspend fun logout() {
        val token = sessionStore.tokens.first().refreshToken
        if (token != null) {
            try {
                authService.logout(token)
            } catch (e: Exception) {
                // TODO: Implement exception handling
            }
        }
        sessionStore.clearSession()
    }

    private fun isFirebaseTokenValid(): Boolean {
        // TODO: Check firebase token (from firebase service)
        return false
    }
}



