package com.ntc.shopree.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart")
    fun observeCart(): Flow<List<CartItemEntity>>

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM cart")
    fun observeTotalQuantity(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CartItemEntity): Long

    @Query("SELECT * FROM cart WHERE product_slug = :slug AND vendor_name = :vendor LIMIT 1")
    suspend fun getItem(slug: String, vendor: String): CartItemEntity?

    @Query(
        """
            UPDATE cart
            SET quantity = quantity + 1
            WHERE product_slug = :slug AND vendor_name = :vendor
        """
    )
    suspend fun incrementQuantity(slug: String, vendor: String)

    @Query(
        """
            UPDATE cart
            SET quantity = quantity - 1
            WHERE product_slug = :slug AND vendor_name = :vendor AND quantity > 1
        """
    )
    suspend fun decrementQuantity(slug: String, vendor: String)


    @Transaction
    suspend fun upsert(item: CartItemEntity) {
        val result = insert(item)
        if (result == -1L) {
            incrementQuantity(item.productSlug, item.vendorName)
        }
    }

    @Query(
        """
        DELETE FROM cart
        WHERE product_slug = :slug
        AND vendor_name = :vendor
    """
    )
    suspend fun remove(slug: String, vendor: String)

    @Query("DELETE FROM cart")
    suspend fun clear()

    @Query(
        """
        SELECT COALESCE(CAST(SUM(quantity * price) AS REAL), 0.0)
        FROM cart
    """
    )
    fun observeTotalPrice(): Flow<Double>
}