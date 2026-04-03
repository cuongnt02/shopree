package com.ntc.shopree.core.network.repository

import com.ntc.shopree.core.model.User
import com.ntc.shopree.core.model.repository.UserRepository
import com.ntc.shopree.core.network.dto.ChangePasswordRequest
import com.ntc.shopree.core.network.dto.UpdateProfileRequest
import com.ntc.shopree.core.network.dto.toUser
import com.ntc.shopree.core.network.service.UserService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun getProfile(): User = userService.getProfile().toUser()

    override suspend fun updateProfile(name: String, phone: String): User =
        userService.updateProfile(UpdateProfileRequest(name = name, phone = phone)).toUser()

    override suspend fun changePassword(currentPassword: String, newPassword: String) {
        userService.changePassword(ChangePasswordRequest(currentPassword = currentPassword, newPassword = newPassword))
    }
}
