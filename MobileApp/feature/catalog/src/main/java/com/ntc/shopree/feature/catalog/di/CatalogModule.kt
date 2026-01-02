package com.ntc.shopree.feature.catalog.di

import com.ntc.shopree.feature.catalog.data.CategoryRepositoryImpl
import com.ntc.shopree.feature.catalog.data.ProductRepositoryImpl
import com.ntc.shopree.feature.catalog.domain.CategoryRepository
import com.ntc.shopree.feature.catalog.domain.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CatalogModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository
}
