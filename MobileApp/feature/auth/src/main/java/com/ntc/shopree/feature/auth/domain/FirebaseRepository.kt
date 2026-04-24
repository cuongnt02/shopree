package com.ntc.shopree.feature.auth.domain

import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {
    suspend fun currentUser(): FirebaseUser?
    suspend fun getTokenId(): String?
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
}
