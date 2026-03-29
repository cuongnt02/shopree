package com.ntc.shopree.feature.auth.data

import android.util.Log
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
        Log.d("DEBUG LOGOUT", "Logout process started. Refresh token: $token")
        if (token != null) {
            try {
                authService.logout(token)
                Log.d("DEBUG_LOGOUT", "Backend logout request successful")
            } catch (e: Exception) {
                // TODO: Implement exception handling
                Log.e("DEBUG_LOGOUT", "Backend logout request failed: ${e.message}")
            }
        }
        sessionStore.clearSession()
        Log.d("DEBUG_LOGOUT", "Local session (DataStore) cleared successfully")
    }

    private fun isFirebaseTokenValid(): Boolean {
        // TODO: Check firebase token (from firebase service)
        return false
    }
}



