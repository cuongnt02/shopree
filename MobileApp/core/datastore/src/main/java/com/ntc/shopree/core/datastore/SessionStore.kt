package com.ntc.shopree.core.datastore
import kotlinx.coroutines.flow.Flow


interface SessionStore {
    val tokens: Flow<SessionToken>
    suspend fun saveSession(accessToken: String, refreshToken: String, expiresAt: Long)
    suspend fun clearSession()

    suspend fun validateSession(accessToken: String, userEmail: String): Boolean
}