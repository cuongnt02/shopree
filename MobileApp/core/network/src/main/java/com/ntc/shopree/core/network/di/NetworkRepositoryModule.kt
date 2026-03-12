package com.ntc.shopree.core.network.di

import com.ntc.shopree.core.model.repository.CategoryRepository
import com.ntc.shopree.core.model.repository.ProductRepository
import com.ntc.shopree.core.network.repository.CategoryRepositoryImpl
import com.ntc.shopree.core.network.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository
}
