package com.ntc.shopree.feature.auth.di

import com.ntc.shopree.feature.auth.data.AuthRepositoryImpl
import com.ntc.shopree.feature.auth.data.FirebaseRepositoryImpl
import com.ntc.shopree.feature.auth.data.remote.service.FirebaseService
import com.ntc.shopree.feature.auth.data.remote.service.impl.FirebaseServiceImpl
import com.ntc.shopree.feature.auth.domain.AuthRepository
import com.ntc.shopree.feature.auth.domain.FirebaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindFirebaseService(impl: FirebaseServiceImpl): FirebaseService

    @Binds
    @Singleton
    abstract fun bindFirebaseRepository(impl: FirebaseRepositoryImpl): FirebaseRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
