package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
  @Query("SELECT * FROM cart_items")
  fun getAllCartItems(): Flow<List<CartItemEntity>>

  @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
  suspend fun getCartItem(productId: String): CartItemEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrUpdate(item: CartItemEntity)

  @Update
  suspend fun update(item: CartItemEntity)

  @Query("DELETE FROM cart_items WHERE productId = :productId")
  suspend fun delete(productId: String)

  @Query("DELETE FROM cart_items")
  suspend fun clearCart()
}
