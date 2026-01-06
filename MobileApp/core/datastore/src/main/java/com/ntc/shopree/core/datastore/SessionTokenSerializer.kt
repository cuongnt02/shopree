package com.ntc.shopree.core.datastore

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


object SessionTokenSerializer: Serializer<SessionToken> {
    override val defaultValue: SessionToken
        get() = SessionToken()

    override suspend fun readFrom(input: InputStream): SessionToken {
        try {
            val data = input.readBytes().decodeToString()
            return Json.decodeFromString(SessionToken.serializer(), data)
        }
        catch (_: SerializationException) {
            return defaultValue
        }
    }

    override suspend fun writeTo(t: SessionToken, output: OutputStream) {
        output.write(Json.encodeToString(SessionToken.serializer(), value = t).encodeToByteArray())
    }
}