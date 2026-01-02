package com.ntc.shopree.feature.auth.domain

import com.ntc.shopree.core.model.Session
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val authRepository: AuthRepository
) {
    // TODO: Handle nullable session and map to a dedicated auth error state.
    suspend operator fun invoke(email: String, password: String): Result<Session?> {
        val idToken = firebaseRepository.getTokenId()
            ?: return Result.failure(Exception("User is not logged in to Firebase or token is missing"))

        return try {
            val session = authRepository.getSession(
                email = email,
                password = password,
                firebaseToken = idToken
            )
            Result.success(session)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}



