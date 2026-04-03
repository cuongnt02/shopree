package com.ntc.shopree.feature.profile.domain

import com.ntc.shopree.core.model.repository.UserRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(currentPassword: String, newPassword: String): Result<Unit> {
        return try {
            userRepository.changePassword(currentPassword, newPassword)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
