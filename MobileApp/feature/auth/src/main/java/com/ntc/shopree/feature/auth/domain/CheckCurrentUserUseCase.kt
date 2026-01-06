package com.ntc.shopree.feature.auth.domain

import javax.inject.Inject

class CheckCurrentUserUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return try {
            val currentUser = firebaseRepository.currentUser()
            if (currentUser != null) {
                Result.success(true)
            } else {
                Result.success(false)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}

