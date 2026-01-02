package com.ntc.shopree.feature.auth.data.remote.service

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseService {
    suspend fun login(username: String, password: String): Result<AuthResult>
    suspend fun currentUser(): FirebaseUser?

    suspend fun getFirebaseIdToken(): String?
}
