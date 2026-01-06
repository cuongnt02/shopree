package com.ntc.shopree.core.datastore

import kotlinx.serialization.Serializable

@Serializable
data class SessionToken(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val expiresAt: Long? = null,
)
