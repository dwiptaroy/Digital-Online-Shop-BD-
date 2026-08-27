package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.CartItemEntity
import com.example.data.model.OrderSubmission
import com.example.data.model.Product
import com.example.data.model.ProductCategory
import com.example.data.model.Testimonial
import com.example.data.model.WishlistItemEntity
import com.example.data.repository.ShopRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class SortOption(val label: String) {
  DEFAULT("Default"),
  PRICE_LOW_HIGH("Price: Low to High"),
  PRICE_HIGH_LOW("Price: High to Low"),
  NAME_AZ("Name: A-Z"),
  RATING("Highest Rated")
}

enum class ActiveTab(val label: String) {
  HOME("Home"),
  SHOP("Shop"),
  CATEGORIES("Categories"),
  ABOUT("About Us"),
  PROMISE("Organic Promise"),
  CONTACT("Contact")
}

data class UiNotification(
  val message: String,
  val timestamp: Long = System.currentTimeMillis()
)

class ShopViewModel(
  private val repository: ShopRepository
) : ViewModel() {

  val categories: List<ProductCategory> = repository.getCategories()
  private val allProducts: List<Product> = repository.getProducts()
  val testimonials: List<Testimonial> = repository.getTestimonials()

  // Navigation & Screen selection
  private val _activeTab = MutableStateFlow(ActiveTab.HOME)
  val activeTab: StateFlow<ActiveTab> = _activeTab.asStateFlow()

  // Search & Filters
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _selectedCategory = MutableStateFlow("All")
  val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

  private val _selectedSort = MutableStateFlow(SortOption.DEFAULT)
  val selectedSort: StateFlow<SortOption> = _selectedSort.asStateFlow()

  // Toast / Snack notifications
  private val _notifications = MutableSharedFlow<UiNotification>()
  val notifications: SharedFlow<UiNotification> = _notifications.asSharedFlow()

  // Modals & Drawers
  private val _isCartOpen = MutableStateFlow(false)
  val isCartOpen: StateFlow<Boolean> = _isCartOpen.asStateFlow()

  private val _isWishlistOpen = MutableStateFlow(false)
  val isWishlistOpen: StateFlow<Boolean> = _isWishlistOpen.asStateFlow()

  private val _isSearchDialogOpen = MutableStateFlow(false)
  val isSearchDialogOpen: StateFlow<Boolean> = _isSearchDialogOpen.asStateFlow()

  private val _isCheckoutOpen = MutableStateFlow(false)
  val isCheckoutOpen: StateFlow<Boolean> = _isCheckoutOpen.asStateFlow()

  private val _selectedProductForDetails = MutableStateFlow<Product?>(null)
  val selectedProductForDetails: StateFlow<Product?> = _selectedProductForDetails.asStateFlow()

  private val _recentOrder = MutableStateFlow<OrderSubmission?>(null)
  val recentOrder: StateFlow<OrderSubmission?> = _recentOrder.asStateFlow()

  // Support / Info dialogs
  private val _supportDialogTitle = MutableStateFlow<String?>(null)
  val supportDialogTitle: StateFlow<String?> = _supportDialogTitle.asStateFlow()

  // Room flows for Cart and Wishlist
  val cartItems: StateFlow<List<CartItemEntity>> = repository.cartItems.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = emptyList()
  )

  val wishlistItems: StateFlow<List<WishlistItemEntity>> = repository.wishlistItems.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = emptyList()
  )

  // Filtered & Sorted products
  val filteredProducts: StateFlow<List<Product>> = combine(
    _searchQuery,
    _selectedCategory,
    _selectedSort
  ) { query, category, sort ->
    var list = allProducts

    if (query.isNotBlank()) {
      val q = query.trim().lowercase()
      list = list.filter {
        it.name.lowercase().contains(q) ||
          it.shortDescription.lowercase().contains(q) ||
          it.category.lowercase().contains(q) ||
          it.origin.lowercase().contains(q)
      }
    }

    if (category != "All") {
      list = list.filter { it.category.equals(category, ignoreCase = true) }
    }

    when (sort) {
      SortOption.DEFAULT -> list
      SortOption.PRICE_LOW_HIGH -> list.sortedBy { it.price }
      SortOption.PRICE_HIGH_LOW -> list.sortedByDescending { it.price }
      SortOption.NAME_AZ -> list.sortedBy { it.name }
      SortOption.RATING -> list.sortedByDescending { it.rating }
    }
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = allProducts
  )

  fun setActiveTab(tab: ActiveTab) {
    _activeTab.value = tab
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun selectCategory(category: String) {
    _selectedCategory.value = category
  }

  fun setSortOption(sort: SortOption) {
    _selectedSort.value = sort
  }

  fun openProductDetails(product: Product) {
    _selectedProductForDetails.value = product
  }

  fun closeProductDetails() {
    _selectedProductForDetails.value = null
  }

  fun openCart() {
    _isCartOpen.value = true
  }

  fun closeCart() {
    _isCartOpen.value = false
  }

  fun openWishlist() {
    _isWishlistOpen.value = true
  }

  fun closeWishlist() {
    _isWishlistOpen.value = false
  }

  fun openSearchDialog() {
    _isSearchDialogOpen.value = true
  }

  fun closeSearchDialog() {
    _isSearchDialogOpen.value = false
  }

  fun openCheckout() {
    _isCartOpen.value = false
    _isCheckoutOpen.value = true
  }

  fun closeCheckout() {
    _isCheckoutOpen.value = false
  }

  fun openSupportDialog(title: String) {
    _supportDialogTitle.value = title
  }

  fun closeSupportDialog() {
    _supportDialogTitle.value = null
  }

  fun addToCart(product: Product, quantity: Int = 1) {
    viewModelScope.launch {
      repository.addToCart(product, quantity)
      _notifications.emit(UiNotification("Added ${product.name} to cart."))
    }
  }

  fun updateCartQuantity(productId: String, delta: Int) {
    viewModelScope.launch {
      repository.updateCartQuantity(productId, delta)
    }
  }

  fun removeFromCart(productId: String) {
    viewModelScope.launch {
      repository.removeFromCart(productId)
      _notifications.emit(UiNotification("Item removed from cart."))
    }
  }

  fun toggleWishlist(product: Product) {
    viewModelScope.launch {
      val isSaved = wishlistItems.value.any { it.productId == product.id }
      repository.toggleWishlist(product, isSaved)
      if (isSaved) {
        _notifications.emit(UiNotification("Removed from wishlist."))
      } else {
        _notifications.emit(UiNotification("Added to wishlist."))
      }
    }
  }

  fun removeFromWishlist(productId: String) {
    viewModelScope.launch {
      repository.removeFromWishlist(productId)
      _notifications.emit(UiNotification("Removed from wishlist."))
    }
  }

  fun moveWishlistToCart(item: WishlistItemEntity) {
    val prod = allProducts.find { it.id == item.productId } ?: return
    viewModelScope.launch {
      repository.addToCart(prod, 1)
      repository.removeFromWishlist(item.productId)
      _notifications.emit(UiNotification("Moved ${item.name} to cart."))
    }
  }

  fun submitOrder(
    name: String,
    phone: String,
    email: String,
    address: String,
    city: String,
    paymentMethod: String,
    total: Int,
    itemCount: Int
  ) {
    viewModelScope.launch {
      val orderNumber = "DOSB-${System.currentTimeMillis().toString().takeLast(6)}"
      val order = OrderSubmission(
        orderId = orderNumber,
        customerName = name,
        phone = phone,
        email = email,
        deliveryAddress = address,
        deliveryCity = city,
        paymentMethod = paymentMethod,
        totalAmount = total,
        itemCount = itemCount
      )
      _recentOrder.value = order
      repository.clearCart()
      _isCheckoutOpen.value = false
      _notifications.emit(UiNotification("Order #$orderNumber placed successfully!"))
    }
  }

  fun dismissOrderConfirmation() {
    _recentOrder.value = null
  }

  fun subscribeNewsletter(email: String): Boolean {
    if (email.contains("@") && email.contains(".")) {
      viewModelScope.launch {
        _notifications.emit(UiNotification("Thank you for subscribing to organic updates!"))
      }
      return true
    }
    return false
  }

  fun submitContactForm(name: String, email: String, phone: String, message: String): Boolean {
    if (name.isNotBlank() && email.contains("@") && message.isNotBlank()) {
      viewModelScope.launch {
        _notifications.emit(UiNotification("Thank you $name! Your message has been sent."))
      }
      return true
    }
    return false
  }
}

class ShopViewModelFactory(
  private val repository: ShopRepository
) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(ShopViewModel::class.java)) {
      return ShopViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
