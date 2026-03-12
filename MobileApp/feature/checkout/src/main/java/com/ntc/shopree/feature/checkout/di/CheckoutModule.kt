package com.ntc.shopree.feature.checkout.di

import com.ntc.shopree.feature.checkout.data.OrderRepositoryImpl
import com.ntc.shopree.feature.checkout.domain.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CheckoutModule {
    @Binds
    @Singleton
    abstract fun bindOrderRepository(impl: OrderRepositoryImpl): OrderRepository
}