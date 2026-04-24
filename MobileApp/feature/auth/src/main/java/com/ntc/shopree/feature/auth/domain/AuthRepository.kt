package com.ntc.shopree.feature.auth.domain

import com.ntc.shopree.core.model.Session

interface AuthRepository {
    suspend fun getSession(identifier: String, password: String, firebaseToken: String): Session?
    suspend fun register(name: String, email: String, phone: String, password: String): Session

    suspend fun logout()
}


