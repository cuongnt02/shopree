package com.ntc.shopree.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CartItemEntity::class], version = 3)
abstract class ShopreeDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao
}