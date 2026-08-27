package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ContactMail
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.AppDatabase
import com.example.data.repository.ShopRepository
import com.example.ui.components.AboutBrandSection
import com.example.ui.components.BrandHeader
import com.example.ui.components.CartDrawer
import com.example.ui.components.CategorySection
import com.example.ui.components.CheckoutModal
import com.example.ui.components.ContactSection
import com.example.ui.components.FooterSection
import com.example.ui.components.HeroSection
import com.example.ui.components.NewsletterSection
import com.example.ui.components.OrderSuccessDialog
import com.example.ui.components.OrganicPromiseSection
import com.example.ui.components.ProductDetailModal
import com.example.ui.components.ProductSection
import com.example.ui.components.PromoBannerSection
import com.example.ui.components.SearchDialog
import com.example.ui.components.SupportDialog
import com.example.ui.components.TestimonialsSection
import com.example.ui.components.WhyChooseUsSection
import com.example.ui.components.WishlistDrawer
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.DiscountRed
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.MutedYellow
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NavInactive
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite
import com.example.ui.viewmodel.ActiveTab
import com.example.ui.viewmodel.ShopViewModel
import com.example.ui.viewmodel.ShopViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        val context = LocalContext.current
        val database = remember { AppDatabase.getDatabase(context) }
        val repository = remember { ShopRepository(database.cartDao(), database.wishlistDao()) }
        val viewModel: ShopViewModel = viewModel(factory = ShopViewModelFactory(repository))

        OrganicShopApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun OrganicShopApp(viewModel: ShopViewModel) {
  val activeTab by viewModel.activeTab.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val selectedCategory by viewModel.selectedCategory.collectAsState()
  val selectedSort by viewModel.selectedSort.collectAsState()
  val filteredProducts by viewModel.filteredProducts.collectAsState()
  val cartItems by viewModel.cartItems.collectAsState()
  val wishlistItems by viewModel.wishlistItems.collectAsState()

  val isCartOpen by viewModel.isCartOpen.collectAsState()
  val isWishlistOpen by viewModel.isWishlistOpen.collectAsState()
  val isSearchOpen by viewModel.isSearchDialogOpen.collectAsState()
  val isCheckoutOpen by viewModel.isCheckoutOpen.collectAsState()
  val selectedProductForDetails by viewModel.selectedProductForDetails.collectAsState()
  val recentOrder by viewModel.recentOrder.collectAsState()
  val supportTitle by viewModel.supportDialogTitle.collectAsState()

  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val scope = rememberCoroutineScope()
  val scrollState = rememberLazyListState()
  val snackbarHostState = remember { SnackbarHostState() }

  // Listen for Toast notifications
  LaunchedEffect(viewModel) {
    viewModel.notifications.collect { notification ->
      snackbarHostState.showSnackbar(
        message = notification.message,
        withDismissAction = true
      )
    }
  }

  val wishlistedIds = remember(wishlistItems) {
    wishlistItems.map { it.productId }.toSet()
  }

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet(
        drawerContainerColor = SurfaceCard,
        drawerShape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp),
        modifier = Modifier.width(300.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
        ) {
          // Brand Header in Drawer
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
          ) {
            Image(
              painter = painterResource(id = R.drawable.img_brand_logo),
              contentDescription = "Digital Online Shop BD",
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(SoftSage),
              contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "Digital Online Shop BD",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = ForestGreen
              )
              Text(
                text = "Pure Food. Naturally Better.",
                style = MaterialTheme.typography.labelSmall,
                color = LeafGreen
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))
          HorizontalDivider(color = EditorialBorder)
          Spacer(modifier = Modifier.height(14.dp))

          // Navigation Links
          val navItems = listOf(
            ActiveTab.HOME to Icons.Default.Home,
            ActiveTab.SHOP to Icons.Default.ShoppingBag,
            ActiveTab.CATEGORIES to Icons.Default.Category,
            ActiveTab.ABOUT to Icons.Default.Info,
            ActiveTab.PROMISE to Icons.Default.Nature,
            ActiveTab.CONTACT to Icons.Default.ContactMail
          )

          navItems.forEach { (tab, icon) ->
            val isSelected = activeTab == tab
            NavigationDrawerItem(
              icon = {
                Icon(
                  imageVector = icon,
                  contentDescription = tab.label,
                  tint = if (isSelected) ForestGreen else DeepTextGreen
                )
              },
              label = {
                Text(
                  text = tab.label,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) ForestGreen else DeepTextGreen
                )
              },
              selected = isSelected,
              onClick = {
                viewModel.setActiveTab(tab)
                scope.launch {
                  drawerState.close()
                  scrollState.scrollToItem(0)
                }
              },
              colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = SoftSage,
                unselectedContainerColor = SurfaceCard
              ),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier.padding(vertical = 2.dp)
            )
          }

          Spacer(modifier = Modifier.height(16.dp))
          HorizontalDivider(color = EditorialBorder)
          Spacer(modifier = Modifier.height(14.dp))

          // Quick Drawer Shortcuts
          DrawerActionRow(
            icon = Icons.Default.ShoppingCart,
            label = "Shopping Cart",
            badge = if (cartItems.isNotEmpty()) cartItems.sumOf { it.quantity }.toString() else null,
            onClick = {
              scope.launch { drawerState.close() }
              viewModel.openCart()
            }
          )

          DrawerActionRow(
            icon = Icons.Default.Favorite,
            label = "Wishlist",
            badge = if (wishlistItems.isNotEmpty()) wishlistItems.size.toString() else null,
            onClick = {
              scope.launch { drawerState.close() }
              viewModel.openWishlist()
            }
          )

          Spacer(modifier = Modifier.weight(1f))

          // Trust Footer in Drawer
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = WarmOffWhite,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = "🌿 100% Organic & Natural",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = ForestGreen
              )
              Text(
                text = "Ethically sourced across Bangladesh",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = DeepTextGreen.copy(alpha = 0.7f)
              )
            }
          }
        }
      }
    }
  ) {
    Scaffold(
      modifier = Modifier.fillMaxSize(),
      snackbarHost = { SnackbarHost(snackbarHostState) },
      topBar = {
        BrandHeader(
          activeTab = activeTab,
          cartCount = cartItems.sumOf { it.quantity },
          wishlistCount = wishlistItems.size,
          onTabSelected = { tab ->
            viewModel.setActiveTab(tab)
            scope.launch { scrollState.scrollToItem(0) }
          },
          onOpenSearch = { viewModel.openSearchDialog() },
          onOpenCart = { viewModel.openCart() },
          onOpenWishlist = { viewModel.openWishlist() },
          onOpenDrawer = { scope.launch { drawerState.open() } },
          onShopOrganicClick = {
            viewModel.setActiveTab(ActiveTab.SHOP)
            viewModel.selectCategory("All")
            scope.launch { scrollState.scrollToItem(0) }
          }
        )
      },
      bottomBar = {
        EditorialBottomNavigation(
          activeTab = activeTab,
          cartCount = cartItems.sumOf { it.quantity },
          wishlistCount = wishlistItems.size,
          onSelectTab = { tab ->
            viewModel.setActiveTab(tab)
            scope.launch { scrollState.scrollToItem(0) }
          },
          onOpenWishlist = { viewModel.openWishlist() },
          onOpenCart = { viewModel.openCart() }
        )
      },
      containerColor = WarmOffWhite
    ) { innerPadding ->
      LazyColumn(
        state = scrollState,
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        when (activeTab) {
          ActiveTab.HOME -> {
            item {
              HeroSection(
                onShopNowClick = {
                  viewModel.setActiveTab(ActiveTab.SHOP)
                  viewModel.selectCategory("All")
                },
                onExploreProductsClick = {
                  viewModel.setActiveTab(ActiveTab.CATEGORIES)
                }
              )
            }
            item {
              CategorySection(
                categories = viewModel.categories,
                selectedCategory = selectedCategory,
                onCategoryClick = { catName ->
                  viewModel.selectCategory(catName)
                  viewModel.setActiveTab(ActiveTab.SHOP)
                }
              )
            }
            item {
              ProductSection(
                products = filteredProducts,
                searchQuery = searchQuery,
                selectedCategory = selectedCategory,
                selectedSort = selectedSort,
                wishlistProductIds = wishlistedIds,
                onSearchChange = { viewModel.setSearchQuery(it) },
                onCategorySelect = { viewModel.selectCategory(it) },
                onSortSelect = { viewModel.setSortOption(it) },
                onProductClick = { viewModel.openProductDetails(it) },
                onAddToCart = { viewModel.addToCart(it) },
                onToggleWishlist = { viewModel.toggleWishlist(it) }
              )
            }
            item {
              WhyChooseUsSection()
            }
            item {
              OrganicPromiseSection()
            }
            item {
              PromoBannerSection(
                onExploreCollection = {
                  viewModel.setActiveTab(ActiveTab.SHOP)
                  viewModel.selectCategory("Organic Spices")
                }
              )
            }
            item {
              AboutBrandSection()
            }
            item {
              TestimonialsSection(testimonials = viewModel.testimonials)
            }
            item {
              NewsletterSection(
                onSubscribe = { viewModel.subscribeNewsletter(it) }
              )
            }
            item {
              ContactSection(
                onSubmitContact = { name, email, phone, msg ->
                  viewModel.submitContactForm(name, email, phone, msg)
                }
              )
            }
            item {
              FooterSection(
                onNavigateTab = { tab ->
                  viewModel.setActiveTab(tab)
                  scope.launch { scrollState.scrollToItem(0) }
                },
                onOpenSupportInfo = { title ->
                  viewModel.openSupportDialog(title)
                }
              )
            }
          }

          ActiveTab.SHOP -> {
            item {
              ProductSection(
                products = filteredProducts,
                searchQuery = searchQuery,
                selectedCategory = selectedCategory,
                selectedSort = selectedSort,
                wishlistProductIds = wishlistedIds,
                onSearchChange = { viewModel.setSearchQuery(it) },
                onCategorySelect = { viewModel.selectCategory(it) },
                onSortSelect = { viewModel.setSortOption(it) },
                onProductClick = { viewModel.openProductDetails(it) },
                onAddToCart = { viewModel.addToCart(it) },
                onToggleWishlist = { viewModel.toggleWishlist(it) }
              )
            }
            item {
              PromoBannerSection(
                onExploreCollection = {
                  viewModel.selectCategory("Dry Fruits")
                }
              )
            }
            item {
              NewsletterSection(
                onSubscribe = { viewModel.subscribeNewsletter(it) }
              )
            }
            item {
              FooterSection(
                onNavigateTab = { tab ->
                  viewModel.setActiveTab(tab)
                  scope.launch { scrollState.scrollToItem(0) }
                },
                onOpenSupportInfo = { title ->
                  viewModel.openSupportDialog(title)
                }
              )
            }
          }

          ActiveTab.CATEGORIES -> {
            item {
              CategorySection(
                categories = viewModel.categories,
                selectedCategory = selectedCategory,
                onCategoryClick = { catName ->
                  viewModel.selectCategory(catName)
                  viewModel.setActiveTab(ActiveTab.SHOP)
                }
              )
            }
            item {
              ProductSection(
                products = filteredProducts,
                searchQuery = searchQuery,
                selectedCategory = selectedCategory,
                selectedSort = selectedSort,
                wishlistProductIds = wishlistedIds,
                onSearchChange = { viewModel.setSearchQuery(it) },
                onCategorySelect = { viewModel.selectCategory(it) },
                onSortSelect = { viewModel.setSortOption(it) },
                onProductClick = { viewModel.openProductDetails(it) },
                onAddToCart = { viewModel.addToCart(it) },
                onToggleWishlist = { viewModel.toggleWishlist(it) }
              )
            }
            item {
              FooterSection(
                onNavigateTab = { tab ->
                  viewModel.setActiveTab(tab)
                  scope.launch { scrollState.scrollToItem(0) }
                },
                onOpenSupportInfo = { title ->
                  viewModel.openSupportDialog(title)
                }
              )
            }
          }

          ActiveTab.ABOUT -> {
            item {
              AboutBrandSection()
            }
            item {
              WhyChooseUsSection()
            }
            item {
              OrganicPromiseSection()
            }
            item {
              TestimonialsSection(testimonials = viewModel.testimonials)
            }
            item {
              FooterSection(
                onNavigateTab = { tab ->
                  viewModel.setActiveTab(tab)
                  scope.launch { scrollState.scrollToItem(0) }
                },
                onOpenSupportInfo = { title ->
                  viewModel.openSupportDialog(title)
                }
              )
            }
          }

          ActiveTab.PROMISE -> {
            item {
              OrganicPromiseSection()
            }
            item {
              WhyChooseUsSection()
            }
            item {
              AboutBrandSection()
            }
            item {
              FooterSection(
                onNavigateTab = { tab ->
                  viewModel.setActiveTab(tab)
                  scope.launch { scrollState.scrollToItem(0) }
                },
                onOpenSupportInfo = { title ->
                  viewModel.openSupportDialog(title)
                }
              )
            }
          }

          ActiveTab.CONTACT -> {
            item {
              ContactSection(
                onSubmitContact = { name, email, phone, msg ->
                  viewModel.submitContactForm(name, email, phone, msg)
                }
              )
            }
            item {
              NewsletterSection(
                onSubscribe = { viewModel.subscribeNewsletter(it) }
              )
            }
            item {
              FooterSection(
                onNavigateTab = { tab ->
                  viewModel.setActiveTab(tab)
                  scope.launch { scrollState.scrollToItem(0) }
                },
                onOpenSupportInfo = { title ->
                  viewModel.openSupportDialog(title)
                }
              )
            }
          }
        }
      }
    }
  }

  // Modals & Bottom Sheets
  if (isCartOpen) {
    CartDrawer(
      cartItems = cartItems,
      onClose = { viewModel.closeCart() },
      onUpdateQuantity = { id, delta -> viewModel.updateCartQuantity(id, delta) },
      onRemoveItem = { id -> viewModel.removeFromCart(id) },
      onProceedToCheckout = { viewModel.openCheckout() }
    )
  }

  if (isWishlistOpen) {
    WishlistDrawer(
      wishlistItems = wishlistItems,
      onClose = { viewModel.closeWishlist() },
      onMoveToCart = { viewModel.moveWishlistToCart(it) },
      onRemoveItem = { id -> viewModel.removeFromWishlist(id) }
    )
  }

  if (isSearchOpen) {
    SearchDialog(
      searchQuery = searchQuery,
      products = filteredProducts,
      onSearchChange = { viewModel.setSearchQuery(it) },
      onProductClick = { viewModel.openProductDetails(it) },
      onClose = { viewModel.closeSearchDialog() }
    )
  }

  if (isCheckoutOpen) {
    CheckoutModal(
      cartItems = cartItems,
      onClose = { viewModel.closeCheckout() },
      onPlaceOrder = { name, phone, email, address, city, payment, total, count ->
        viewModel.submitOrder(name, phone, email, address, city, payment, total, count)
      }
    )
  }

  selectedProductForDetails?.let { product ->
    ProductDetailModal(
      product = product,
      isWishlisted = wishlistedIds.contains(product.id),
      onDismiss = { viewModel.closeProductDetails() },
      onAddToCart = { prod, qty -> viewModel.addToCart(prod, qty) },
      onToggleWishlist = { prod -> viewModel.toggleWishlist(prod) }
    )
  }

  recentOrder?.let { order ->
    OrderSuccessDialog(
      order = order,
      onDismiss = { viewModel.dismissOrderConfirmation() }
    )
  }

  supportTitle?.let { title ->
    SupportDialog(
      title = title,
      onDismiss = { viewModel.closeSupportDialog() }
    )
  }
}

@Composable
private fun DrawerActionRow(
  icon: ImageVector,
  label: String,
  badge: String?,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .clickable(onClick = onClick)
      .padding(horizontal = 14.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(imageVector = icon, contentDescription = null, tint = ForestGreen, modifier = Modifier.size(20.dp))
      Spacer(modifier = Modifier.width(12.dp))
      Text(text = label, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium), color = DeepTextGreen)
    }

    if (badge != null) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .background(ForestGreen)
          .padding(horizontal = 8.dp, vertical = 2.dp)
      ) {
        Text(
          text = badge,
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = SoftCream
        )
      }
    }
  }
}

@Composable
private fun EditorialBottomNavigation(
  activeTab: ActiveTab,
  cartCount: Int,
  wishlistCount: Int,
  onSelectTab: (ActiveTab) -> Unit,
  onOpenWishlist: () -> Unit,
  onOpenCart: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
    color = SurfaceCard,
    shadowElevation = 8.dp,
    border = androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 10.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Home
      EditorialNavItem(
        icon = if (activeTab == ActiveTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
        label = "Home",
        isSelected = activeTab == ActiveTab.HOME,
        onClick = { onSelectTab(ActiveTab.HOME) },
        modifier = Modifier.testTag("bottom_nav_home")
      )

      // Shop
      EditorialNavItem(
        icon = if (activeTab == ActiveTab.SHOP) Icons.Filled.ShoppingBag else Icons.Outlined.ShoppingBag,
        label = "Shop",
        isSelected = activeTab == ActiveTab.SHOP,
        onClick = { onSelectTab(ActiveTab.SHOP) },
        modifier = Modifier.testTag("bottom_nav_shop")
      )

      // Categories
      EditorialNavItem(
        icon = if (activeTab == ActiveTab.CATEGORIES) Icons.Filled.Category else Icons.Outlined.Category,
        label = "Categories",
        isSelected = activeTab == ActiveTab.CATEGORIES,
        onClick = { onSelectTab(ActiveTab.CATEGORIES) },
        modifier = Modifier.testTag("bottom_nav_categories")
      )

      // Wishlist
      EditorialNavItem(
        icon = if (wishlistCount > 0) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
        label = "Wishlist",
        isSelected = false,
        badgeCount = wishlistCount,
        onClick = onOpenWishlist,
        modifier = Modifier.testTag("bottom_nav_wishlist")
      )

      // Cart
      EditorialNavItem(
        icon = if (cartCount > 0) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
        label = "Cart",
        isSelected = false,
        badgeCount = cartCount,
        onClick = onOpenCart,
        modifier = Modifier.testTag("bottom_nav_cart")
      )
    }
  }
}

@Composable
private fun EditorialNavItem(
  icon: ImageVector,
  label: String,
  isSelected: Boolean,
  badgeCount: Int = 0,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .clickable(onClick = onClick)
      .padding(horizontal = 8.dp, vertical = 4.dp)
  ) {
    BadgedBox(
      badge = {
        if (badgeCount > 0) {
          Badge(
            containerColor = ForestGreen,
            contentColor = SoftCream
          ) {
            Text(
              text = badgeCount.toString(),
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 9.sp
              )
            )
          }
        }
      }
    ) {
      Box(
        modifier = Modifier
          .clip(CircleShape)
          .background(if (isSelected) SoftSage else androidx.compose.ui.graphics.Color.Transparent)
          .padding(6.dp),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = label,
          tint = if (isSelected) ForestGreen else NavInactive,
          modifier = Modifier.size(22.dp)
        )
      }
    }
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        fontSize = 10.sp
      ),
      color = if (isSelected) ForestGreen else NavInactive
    )
  }
}
