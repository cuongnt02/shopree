package com.ntc.service

import com.ntc.service.dto.ChangePasswordRequest
import com.ntc.service.dto.UpdateProfileRequest
import com.ntc.service.dto.UserProfileResponse
import java.util.UUID

interface UserService {
    fun getProfile(userId: UUID): UserProfileResponse
    fun updateProfile(userId: UUID, request: UpdateProfileRequest): UserProfileResponse
    fun changePassword(userId: UUID, request: ChangePasswordRequest)
}