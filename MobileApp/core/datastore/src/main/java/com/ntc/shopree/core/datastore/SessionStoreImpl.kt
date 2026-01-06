package com.ntc.shopree.core.datastore

import android.content.Context
import android.os.Build
import androidx.datastore.dataStore
import com.ntc.shopree.core.datastore.utils.JwtDecoder
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long

val Context.sessionDataStore by dataStore(
    fileName = "session_store.json", serializer = SessionTokenSerializer
)

class SessionStoreImpl(
    private val context: Context
) : SessionStore {
    override val tokens: Flow<SessionToken> = context.sessionDataStore.data

    override suspend fun saveSession(
        accessToken: String, refreshToken: String, expiresAt: Long
    ) {
        context.sessionDataStore.updateData {
            it.copy(accessToken = accessToken, refreshToken = refreshToken, expiresAt = expiresAt)
        }
    }

    override suspend fun clearSession() {
        context.sessionDataStore.updateData {
            it.copy(accessToken = null, refreshToken = null, expiresAt = null)
        }
    }

    override suspend fun validateSession(accessToken: String, userEmail: String): Boolean {
        val payload = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            JwtDecoder.decodeToken(accessToken)
        } else {
            throw Exception("VERSION.SDK_INT < O")
        }
        val exp = payload?.get("exp")!!.jsonPrimitive.long    // expiration (seconds)
        val userId = payload["sub"]!!.jsonPrimitive.content   // subject
        // TODO: Determine if should validate role in the future
//        val roles = payload["roles"]!!.jsonPrimitive.content
        return userEmail == userId && !isExpired(exp)

    }

    private fun isExpired(expSeconds: Long): Boolean {
        val nowSeconds = System.currentTimeMillis() / 1000
        return nowSeconds > expSeconds
    }

}