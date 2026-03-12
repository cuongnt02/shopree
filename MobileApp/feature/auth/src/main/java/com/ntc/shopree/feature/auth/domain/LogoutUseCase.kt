package com.ntc.shopree.feature.auth.domain

import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> = try {
        authRepository.logout()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}