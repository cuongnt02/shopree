package com.ntc.shopree.feature.profile.domain

import com.ntc.shopree.core.model.User
import com.ntc.shopree.core.model.repository.UserRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<User> {
        return try {
            Result.success(userRepository.getProfile())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
