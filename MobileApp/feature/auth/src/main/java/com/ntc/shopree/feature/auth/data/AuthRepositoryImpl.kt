package com.ntc.shopree.feature.auth.data

import com.ntc.shopree.core.model.Session
import com.ntc.shopree.core.network.dto.toSession
import com.ntc.shopree.core.network.service.AuthService
import com.ntc.shopree.feature.auth.domain.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun getSession(email: String, password: String, firebaseToken: String): Session {
        return authService.loginWithEmailAndPassword(email, password).toSession()
    }

    private fun isFirebaseTokenValid(): Boolean {
        // Check firebase token (from firebase service)
        return false
    }
}



