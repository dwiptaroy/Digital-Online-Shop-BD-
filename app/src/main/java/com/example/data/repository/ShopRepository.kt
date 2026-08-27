package com.example.data.repository

import com.example.R
import com.example.data.local.CartDao
import com.example.data.local.WishlistDao
import com.example.data.model.CartItemEntity
import com.example.data.model.Product
import com.example.data.model.ProductCategory
import com.example.data.model.Testimonial
import com.example.data.model.WishlistItemEntity
import kotlinx.coroutines.flow.Flow

class ShopRepository(
  private val cartDao: CartDao,
  private val wishlistDao: WishlistDao
) {
  val cartItems: Flow<List<CartItemEntity>> = cartDao.getAllCartItems()
  val wishlistItems: Flow<List<WishlistItemEntity>> = wishlistDao.getAllWishlistItems()

  fun isFavorite(productId: String): Flow<Boolean> = wishlistDao.isItemInWishlist(productId)

  suspend fun addToCart(product: Product, quantity: Int = 1) {
    val existing = cartDao.getCartItem(product.id)
    if (existing != null) {
      cartDao.update(existing.copy(quantity = existing.quantity + quantity))
    } else {
      cartDao.insertOrUpdate(
        CartItemEntity(
          productId = product.id,
          name = product.name,
          price = product.price,
          quantity = quantity,
          imageRes = product.imageRes,
          unit = product.unit
        )
      )
    }
  }

  suspend fun updateCartQuantity(productId: String, delta: Int) {
    val existing = cartDao.getCartItem(productId) ?: return
    val newQty = existing.quantity + delta
    if (newQty <= 0) {
      cartDao.delete(productId)
    } else {
      cartDao.update(existing.copy(quantity = newQty))
    }
  }

  suspend fun removeFromCart(productId: String) {
    cartDao.delete(productId)
  }

  suspend fun clearCart() {
    cartDao.clearCart()
  }

  suspend fun toggleWishlist(product: Product, isCurrentlyInWishlist: Boolean) {
    if (isCurrentlyInWishlist) {
      wishlistDao.removeFromWishlist(product.id)
    } else {
      wishlistDao.addToWishlist(
        WishlistItemEntity(
          productId = product.id,
          name = product.name,
          price = product.price,
          category = product.category,
          imageRes = product.imageRes,
          unit = product.unit
        )
      )
    }
  }

  suspend fun removeFromWishlist(productId: String) {
    wishlistDao.removeFromWishlist(productId)
  }

  // Categories list
  fun getCategories(): List<ProductCategory> = listOf(
    ProductCategory(
      id = "fruits",
      name = "Fresh Fruits",
      shortDescription = "Naturally ripened sweet mangoes, bananas & orchard picks",
      imageRes = R.drawable.img_cat_fruits,
      itemCount = 18
    ),
    ProductCategory(
      id = "vegetables",
      name = "Organic Vegetables",
      shortDescription = "Farm-fresh leafy greens harvested without chemical sprays",
      imageRes = R.drawable.img_hero_organic,
      itemCount = 24
    ),
    ProductCategory(
      id = "honey",
      name = "Natural Honey",
      shortDescription = "100% raw wildflower & Sundarbans artisanal nectar",
      imageRes = R.drawable.img_cat_honey,
      itemCount = 9
    ),
    ProductCategory(
      id = "dry_fruits",
      name = "Dry Fruits",
      shortDescription = "Premium hand-selected dates, almonds, cashews & walnuts",
      imageRes = R.drawable.img_promo_harvest,
      itemCount = 14
    ),
    ProductCategory(
      id = "spices",
      name = "Organic Spices",
      shortDescription = "Cold-pressed mustard oil, turmeric & aromatic seeds",
      imageRes = R.drawable.img_promo_harvest,
      itemCount = 16
    ),
    ProductCategory(
      id = "healthy_snacks",
      name = "Healthy Snacks",
      shortDescription = "Chia seeds, whole grains & nutritious roasted snacks",
      imageRes = R.drawable.img_cat_fruits,
      itemCount = 12
    )
  )

  // Products list
  fun getProducts(): List<Product> = listOf(
    Product(
      id = "prod_honey_1",
      name = "Premium Natural Honey",
      category = "Natural Honey",
      shortDescription = "100% Raw unfiltered wild honey from deep Sundarbans forest nectar.",
      fullDescription = "Our Premium Natural Honey is unpasteurized, unprocessed, and extracted directly from wild hives in the mangrove forests. It retains all natural enzymes, pollens, and antioxidants, offering a rich caramel aroma and smooth, velvety texture. (Sample / Demo Data)",
      price = 850,
      prevPrice = 950,
      discountPercent = 10,
      rating = 4.9f,
      reviewCount = 128,
      unit = "500g Glass Jar",
      imageRes = R.drawable.img_cat_honey,
      origin = "Sundarbans Mangrove, Bangladesh",
      highlights = listOf("100% Raw & Unfiltered", "Naturally Rich in Antioxidants", "Zero Artificial Additives")
    ),
    Product(
      id = "prod_mango_1",
      name = "Organic Mango (Himsagar)",
      category = "Fresh Fruits",
      shortDescription = "Naturally tree-ripened, intensely aromatic and succulent heirloom mangoes.",
      fullDescription = "Grown in certified eco-orchards in Rajshahi without carbide or synthetic ripening agents. Hand-picked at peak ripeness for an unmatched sweetness and fiberless melt-in-mouth texture. (Sample / Demo Data)",
      price = 450,
      prevPrice = 520,
      discountPercent = 13,
      rating = 4.9f,
      reviewCount = 94,
      unit = "1 kg Box",
      imageRes = R.drawable.img_cat_fruits,
      origin = "Rajshahi, Bangladesh",
      highlights = listOf("Naturally Tree-Ripened", "Zero Carbide / Chemicals", "Farm-to-Door in 24 Hours")
    ),
    Product(
      id = "prod_banana_1",
      name = "Fresh Organic Banana (Sagor)",
      category = "Fresh Fruits",
      shortDescription = "Sweet, creamy, nutrient-rich local bananas grown on regenerative soil.",
      fullDescription = "Harvested from smallholder family farms using compost soil amendments. Naturally delicious and high in potassium, perfect for morning breakfast and energy boosts. (Sample / Demo Data)",
      price = 120,
      prevPrice = 140,
      discountPercent = 14,
      rating = 4.7f,
      reviewCount = 62,
      unit = "1 Dozen",
      imageRes = R.drawable.img_cat_fruits,
      origin = "Narsingdi, Bangladesh",
      highlights = listOf("100% Naturally Ripened", "Rich in Potassium", "Daily Morning Harvest")
    ),
    Product(
      id = "prod_oil_1",
      name = "Pure Mustard Oil (Cold-Pressed)",
      category = "Organic Spices",
      shortDescription = "Traditional wood-pressed virgin mustard oil with sharp natural pungency.",
      fullDescription = "Extracted from premium native black mustard seeds using traditional slow-speed wooden Ghani methods. Preserves natural vitamins, essential fatty acids, and authentic Bengali culinary aroma. (Sample / Demo Data)",
      price = 380,
      prevPrice = 420,
      discountPercent = 9,
      rating = 4.8f,
      reviewCount = 88,
      unit = "1 Litre Bottle",
      imageRes = R.drawable.img_promo_harvest,
      origin = "Sirajganj, Bangladesh",
      highlights = listOf("Cold Ghani Pressed", "Pungent Aromatic Flavor", "Unrefined & Virgin")
    ),
    Product(
      id = "prod_dates_1",
      name = "Premium Medjool Dates",
      category = "Dry Fruits",
      shortDescription = "Large, soft, caramel-like organic dates packed with natural energy.",
      fullDescription = "Carefully sorted top-grade dates with delicate thin skin and plush, sweet flesh. A wholesome natural sweetener and nutrient-dense healthy snack for families. (Sample / Demo Data)",
      price = 650,
      prevPrice = 750,
      discountPercent = 13,
      rating = 4.9f,
      reviewCount = 112,
      unit = "500g Pack",
      imageRes = R.drawable.img_promo_harvest,
      origin = "Ethically Imported & Selected",
      highlights = listOf("Soft & Fleshy Texture", "Natural Energy Booster", "No Added Sugar")
    ),
    Product(
      id = "prod_turmeric_1",
      name = "Organic Turmeric Powder",
      category = "Organic Spices",
      shortDescription = "High-curcumin sun-dried turmeric ground slowly on traditional stone mills.",
      fullDescription = "Sourced from hill tract organic gardens. Retains high natural essential oils and curcumin levels above 5%, giving deep golden color and distinct therapeutic potency. (Sample / Demo Data)",
      price = 220,
      prevPrice = null,
      discountPercent = null,
      rating = 4.8f,
      reviewCount = 54,
      unit = "250g Sealed Pouch",
      imageRes = R.drawable.img_promo_harvest,
      origin = "Chittagong Hill Tracts, Bangladesh",
      highlights = listOf("High Curcumin Content (>5%)", "Sun-Dried & Stone Ground", "No Lead or Artificial Color")
    ),
    Product(
      id = "prod_dryfruits_1",
      name = "Mixed Royal Dry Fruits",
      category = "Dry Fruits",
      shortDescription = "Crunchy gourmet mix of Californian almonds, Persian walnuts & cashews.",
      fullDescription = "An expertly proportioned mix of high-grade raw almonds, light walnuts, golden raisins, and buttery roasted cashews. Clean snack for work and study. (Sample / Demo Data)",
      price = 980,
      prevPrice = 1100,
      discountPercent = 11,
      rating = 5.0f,
      reviewCount = 76,
      unit = "400g Airtight Jar",
      imageRes = R.drawable.img_promo_harvest,
      origin = "Hand-Selected Premium Blends",
      highlights = listOf("Zero Added Salt or Oil", "Rich in Omega-3", "Airtight Freshness Seal")
    ),
    Product(
      id = "prod_coconut_1",
      name = "Natural Coconut Oil",
      category = "Organic Spices",
      shortDescription = "Virgin cold-pressed coconut oil for edible wellness and natural care.",
      fullDescription = "Crafted from fresh coastal coconuts within hours of harvest. Pure water-white color, heavenly tropical coconut aroma, and high lauric acid content. (Sample / Demo Data)",
      price = 420,
      prevPrice = 460,
      discountPercent = 8,
      rating = 4.8f,
      reviewCount = 47,
      unit = "500ml Glass Jar",
      imageRes = R.drawable.img_hero_organic,
      origin = "Barishal Coastal Groves, Bangladesh",
      highlights = listOf("Centrifuged Extra Virgin", "Food Grade & Raw", "Deep Nourishment")
    ),
    Product(
      id = "prod_chia_1",
      name = "Organic Chia Seeds",
      category = "Healthy Snacks",
      shortDescription = "Nutrient-dense raw chia seeds rich in dietary fiber and plant Omega-3.",
      fullDescription = "Clean, triple-cleaned organic black chia seeds. Swells easily in smoothies, puddings, oats, and lemonade to support digestive wellness and sustained vitality. (Sample / Demo Data)",
      price = 350,
      prevPrice = 390,
      discountPercent = 10,
      rating = 4.9f,
      reviewCount = 89,
      unit = "250g Jar",
      imageRes = R.drawable.img_cat_fruits,
      origin = "Organic Certified Farms",
      highlights = listOf("Triple Cleaned & Pure", "High Dietary Fiber", "Easy Daily Superfood")
    ),
    Product(
      id = "prod_veg_1",
      name = "Fresh Green Veggies Pack",
      category = "Organic Vegetables",
      shortDescription = "Assorted daily seasonal greens: tender spinach, coriander, and gourds.",
      fullDescription = "Crisp, vibrant greens harvested from partner organic community plots at dawn. Packed in biodegradable breathable covers to preserve garden freshness. (Sample / Demo Data)",
      price = 280,
      prevPrice = null,
      discountPercent = null,
      rating = 4.7f,
      reviewCount = 39,
      unit = "1 Daily Harvest Basket",
      imageRes = R.drawable.img_hero_organic,
      origin = "Savar & Gazipur, Bangladesh",
      highlights = listOf("Harvested at Dawn", "Pesticide-Free Tested", "Eco-Friendly Packing")
    )
  )

  fun getTestimonials(): List<Testimonial> = listOf(
    Testimonial(
      id = "test_1",
      author = "Dr. Farhana Ahmed",
      role = "Demo Customer • Verified Buyer",
      location = "Gulshan, Dhaka",
      content = "The Sundarbans raw honey and cold-pressed mustard oil taste so authentic and pure! You can immediately sense the natural fragrance compared to commercial brands. A delightful experience.",
      rating = 5,
      date = "Sample Review • June 2026"
    ),
    Testimonial(
      id = "test_2",
      author = "Tanvir Hossain",
      role = "Demo Customer • Health Enthusiast",
      location = "Uttara, Dhaka",
      content = "Beautiful presentation and a very easy shopping experience. The organic mangoes arrived in pristine condition, naturally sweet with zero chemical smell. Highly recommended for families.",
      rating = 5,
      date = "Sample Review • July 2026"
    ),
    Testimonial(
      id = "test_3",
      author = "Nusrat Jahan",
      role = "Demo Customer • Nutritionist",
      location = "Dhanmondi, Dhaka",
      content = "I ordered the royal mixed dry fruits and turmeric powder. The packaging is eco-conscious and the product cleanliness exceeded my expectations. Glad to have a reliable organic brand.",
      rating = 5,
      date = "Sample Review • August 2026"
    )
  )
}
