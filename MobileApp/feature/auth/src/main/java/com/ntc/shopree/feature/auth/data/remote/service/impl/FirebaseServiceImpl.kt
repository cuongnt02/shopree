package com.ntc.shopree.feature.auth.data.remote.service.impl

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ntc.shopree.feature.auth.data.remote.service.FirebaseService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor(
    private val auth: FirebaseAuth
) : FirebaseService {
    override suspend fun login(
        username: String, password: String
    ): Result<AuthResult> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(username, password).await()
            Result.success(authResult)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun currentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun getFirebaseIdToken(): String? {
        return auth.currentUser?.getIdToken(true)?.await()?.token
    }
}


