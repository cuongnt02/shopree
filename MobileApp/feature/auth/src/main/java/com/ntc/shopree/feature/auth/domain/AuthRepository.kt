package com.ntc.shopree.feature.auth.domain

import com.ntc.shopree.core.model.Session

interface AuthRepository {
    suspend fun getSession(email: String, password: String, firebaseToken: String): Session?
}


