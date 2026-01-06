package com.ntc.shopree.core.datastore.di

import android.content.Context
import com.ntc.shopree.core.datastore.DataStoreSessionTokenProvider
import com.ntc.shopree.core.datastore.SessionStore
import com.ntc.shopree.core.datastore.SessionStoreImpl
import com.ntc.shopree.core.datastore.SessionTokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionStoreModule {
    @Provides
    @Singleton
    fun provideSessionStore(@ApplicationContext context: Context): SessionStore =
        SessionStoreImpl(context)

    @Provides
    @Singleton
    fun provideSessionTokenProvider(@ApplicationContext context: Context): SessionTokenProvider =
        DataStoreSessionTokenProvider(context)
}
