package com.ntc.shopree.core.network.service

import com.ntc.shopree.core.network.dto.LoginResponse

interface AuthService {
    suspend fun loginWithEmailAndPassword(email: String, password: String): LoginResponse
}

