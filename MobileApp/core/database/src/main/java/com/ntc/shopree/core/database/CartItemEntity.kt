package com.ntc.shopree.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.TEXT)
    val id: String,
    @ColumnInfo(name = "product_slug", typeAffinity = ColumnInfo.TEXT)
    val productSlug: String,
    @ColumnInfo(name = "vendor_name", typeAffinity = ColumnInfo.TEXT)
    val vendorName: String,
    @ColumnInfo(name = "product_name", typeAffinity = ColumnInfo.TEXT)
    val productName: String,
    @ColumnInfo(name = "quantity", typeAffinity = ColumnInfo.INTEGER)
    val quantity: Int,
    @ColumnInfo(name = "price", typeAffinity = ColumnInfo.REAL)
    val price: Double,
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.TEXT)
    val imageUrl: String,
)