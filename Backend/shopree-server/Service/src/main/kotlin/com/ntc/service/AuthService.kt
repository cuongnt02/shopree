package com.ntc.service

import com.ntc.service.dto.AuthResult

interface AuthService {
    fun login(username: String, password: String): AuthResult
}