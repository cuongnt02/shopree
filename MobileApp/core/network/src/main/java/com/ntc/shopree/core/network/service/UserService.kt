package com.ntc.shopree.core.network.service

import com.ntc.shopree.core.network.dto.ChangePasswordRequest
import com.ntc.shopree.core.network.dto.UpdateProfileRequest
import com.ntc.shopree.core.network.dto.UserResponse

interface UserService {
    suspend fun getProfile(): UserResponse
    suspend fun updateProfile(request: UpdateProfileRequest): UserResponse
    suspend fun changePassword(request: ChangePasswordRequest)
}
