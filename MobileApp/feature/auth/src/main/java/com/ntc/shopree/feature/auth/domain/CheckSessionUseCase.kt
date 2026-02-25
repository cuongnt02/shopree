package com.ntc.shopree.feature.auth.domain

import com.ntc.shopree.core.datastore.SessionStore
import com.ntc.shopree.core.datastore.utils.JwtDecoder
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import javax.inject.Inject

class CheckSessionUseCase @Inject constructor(
    private val sessionStore: SessionStore
) {
    // TODO: More descriptive results
    suspend operator fun invoke(): Result<Boolean> {
        return try {
            val token = sessionStore.tokens.firstOrNull()
            if (token?.accessToken == null) return Result.success(false)
            val payload =
                JwtDecoder.decodeToken(token.accessToken!!) ?: return Result.success(false)
            val exp = payload["exp"]?.jsonPrimitive?.long ?: return Result.success(false)
            val userId = payload["sub"]?.jsonPrimitive?.content ?: return Result.success(false)

            val isExpired = (System.currentTimeMillis() / 1000) > exp
            if (isExpired) return Result.success(false)
            Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}