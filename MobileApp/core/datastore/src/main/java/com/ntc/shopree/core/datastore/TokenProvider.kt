package com.ntc.shopree.core.datastore

interface SessionTokenProvider {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun isSessionValid(): Boolean
}