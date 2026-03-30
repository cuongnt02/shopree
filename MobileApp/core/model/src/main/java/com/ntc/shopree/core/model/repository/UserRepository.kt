package com.ntc.shopree.core.model.repository

import com.ntc.shopree.core.model.User

interface UserRepository {
    suspend fun getProfile(): User
}
