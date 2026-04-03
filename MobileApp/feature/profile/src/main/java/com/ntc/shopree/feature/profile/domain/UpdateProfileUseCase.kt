package com.ntc.shopree.feature.profile.domain

import com.ntc.shopree.core.model.User
import com.ntc.shopree.core.model.repository.UserRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(name: String, phone: String): Result<User> {
        return try {
            Result.success(userRepository.updateProfile(name, phone))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
