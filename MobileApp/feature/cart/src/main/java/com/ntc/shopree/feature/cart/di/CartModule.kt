package com.ntc.shopree.feature.cart.di

import com.ntc.shopree.core.database.CartDao
import com.ntc.shopree.feature.cart.data.CartRepositoryImpl
import com.ntc.shopree.feature.cart.domain.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {
    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository =
        CartRepositoryImpl(cartDao = cartDao)
}