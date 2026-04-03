package com.ntc.shopree.core.model.repository

import com.ntc.shopree.core.model.User

interface UserRepository {
    suspend fun getProfile(): User
    suspend fun updateProfile(name: String, phone: String): User
    suspend fun changePassword(currentPassword: String, newPassword: String)
}
