package com.ntc.shopree.core.datastore.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import java.util.Base64

object JwtDecoder {
    fun decodeToken(token: String): JsonObject? {
        return try {
            val parts = token.split(".")

            if (parts.size != 3) return null
            val payload = parts[1]
            val decodedPayload = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String(Base64.getUrlDecoder().decode(payload), charset = Charsets.UTF_8)
            } else {
                throw Exception("VERSION.SDK_INT < O")
            }
            Json.parseToJsonElement(decodedPayload).jsonObject
        } catch (e: Exception) {
            null
        }
    }
}