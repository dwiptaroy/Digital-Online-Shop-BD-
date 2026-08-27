package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.WishlistItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {
  @Query("SELECT * FROM wishlist_items")
  fun getAllWishlistItems(): Flow<List<WishlistItemEntity>>

  @Query("SELECT EXISTS(SELECT 1 FROM wishlist_items WHERE productId = :productId)")
  fun isItemInWishlist(productId: String): Flow<Boolean>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addToWishlist(item: WishlistItemEntity)

  @Query("DELETE FROM wishlist_items WHERE productId = :productId")
  suspend fun removeFromWishlist(productId: String)

  @Query("DELETE FROM wishlist_items")
  suspend fun clearWishlist()
}
