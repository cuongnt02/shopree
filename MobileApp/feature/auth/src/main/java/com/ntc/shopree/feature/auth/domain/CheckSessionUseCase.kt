package com.ntc.shopree.feature.auth.domain

import com.ntc.shopree.core.datastore.SessionStore
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class CheckSessionUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository, private val sessionStore: SessionStore
) {
    // TODO: More descriptive results
    suspend operator fun invoke(): Result<Boolean> {
        return try {
            val currentUser = firebaseRepository.currentUser()!!
            val token = sessionStore.tokens.firstOrNull()
            if (token != null) {
                return if (sessionStore.validateSession(token.accessToken!!, currentUser.email!!)) {
                    Result.success(true)
                } else {
                    Result.success(false)
                }
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}