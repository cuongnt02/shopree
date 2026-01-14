package com.ntc.shopree.core.database.di

import android.content.Context
import androidx.room.Room
import com.ntc.shopree.core.database.ShopreeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ShopreeDatabase {
        val db = Room.databaseBuilder(
            context = context, klass = ShopreeDatabase::class.java, name = "shopree-database"
        )
        return db.build()
    }

    @Provides
    @Singleton
    fun provideCartDao(db: ShopreeDatabase) = db.cartDao()

}