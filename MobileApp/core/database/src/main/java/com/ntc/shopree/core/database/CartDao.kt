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


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CartItemEntity): Long

    @Query("SELECT * FROM cart WHERE id = :id")
    suspend fun getItemById(id: String): CartItemEntity?

    @Query(
        """
            UPDATE cart
            SET quantity = quantity + 1
            WHERE id = :id
        """
    )
    suspend fun incrementQuantity(id: String)

    @Query(
        """
            UPDATE cart
            SET quantity = quantity - 1
            WHERE id = :id AND quantity > 1
        """
    )
    suspend fun decrementQuantity(id: String)


    @Transaction
    suspend fun upsert(item: CartItemEntity) {
        val result = insert(item)
        if (result == -1L) {
            incrementQuantity(item.id)
        }
    }

    @Query("""
        DELETE FROM cart
        WHERE product_slug = :productSlug
        AND vendor_name = :vendorName
    """)
    suspend fun remove(productSlug: String, vendorName: String)

    @Query("DELETE FROM cart")
    suspend fun clear()
}