package com.ntc.shopree.core.datastore

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.first

class DataStoreSessionTokenProvider(
    private val context: Context
): SessionTokenProvider {
    override suspend fun getAccessToken(): String? {
        Log.d("DataStoreSessionTokenProvider", "Getting access token ${context.sessionDataStore.data.first().accessToken}")
        return context.sessionDataStore.data.first().accessToken
    }

    override suspend fun getRefreshToken(): String? {
        return context.sessionDataStore.data.first().refreshToken
    }

}