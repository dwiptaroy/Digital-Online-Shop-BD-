package com.example.data.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Product(
  val id: String,
  val name: String,
  val category: String,
  val shortDescription: String,
  val fullDescription: String,
  val price: Int, // In Bangladeshi Taka (৳)
  val prevPrice: Int? = null,
  val discountPercent: Int? = null,
  val rating: Float = 4.8f,
  val reviewCount: Int = 36,
  val isOrganic: Boolean = true,
  val unit: String = "500g",
  @DrawableRes val imageRes: Int,
  val origin: String = "Sundarbans / Sylhet / Rajshahi, Bangladesh",
  val highlights: List<String> = listOf("100% Pure & Raw", "Sustainably Harvested", "Chemical-Free")
)

data class ProductCategory(
  val id: String,
  val name: String,
  val shortDescription: String,
  @DrawableRes val imageRes: Int,
  val itemCount: Int
)

@Entity(tableName = "cart_items")
data class CartItemEntity(
  @PrimaryKey val productId: String,
  val name: String,
  val price: Int,
  val quantity: Int,
  @DrawableRes val imageRes: Int,
  val unit: String
)

@Entity(tableName = "wishlist_items")
data class WishlistItemEntity(
  @PrimaryKey val productId: String,
  val name: String,
  val price: Int,
  val category: String,
  @DrawableRes val imageRes: Int,
  val unit: String
)

data class Testimonial(
  val id: String,
  val author: String,
  val role: String = "Demo Customer",
  val location: String,
  val content: String,
  val rating: Int = 5,
  val verifiedPurchase: Boolean = true,
  val date: String = "Sample Review"
)

data class OrderSubmission(
  val orderId: String,
  val customerName: String,
  val phone: String,
  val email: String,
  val deliveryAddress: String,
  val deliveryCity: String,
  val paymentMethod: String,
  val totalAmount: Int,
  val itemCount: Int,
  val timestamp: Long = System.currentTimeMillis()
)
