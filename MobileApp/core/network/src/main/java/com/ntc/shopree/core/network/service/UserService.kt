package com.ntc.shopree.core.network.service

import com.ntc.shopree.core.network.dto.UserResponse

interface UserService {
    suspend fun getProfile(): UserResponse
}
