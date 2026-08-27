package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.DiscountRed
import com.example.ui.theme.DiscountRedBg
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.StarGold
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.WarmOffWhite
import com.example.ui.viewmodel.SortOption

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductSection(
  products: List<Product>,
  searchQuery: String,
  selectedCategory: String,
  selectedSort: SortOption,
  wishlistProductIds: Set<String>,
  onSearchChange: (String) -> Unit,
  onCategorySelect: (String) -> Unit,
  onSortSelect: (SortOption) -> Unit,
  onProductClick: (Product) -> Unit,
  onAddToCart: (Product) -> Unit,
  onToggleWishlist: (Product) -> Unit,
  modifier: Modifier = Modifier
) {
  var sortMenuExpanded by remember { mutableStateOf(false) }

  val filterCategories = listOf(
    "All",
    "Fresh Fruits",
    "Organic Vegetables",
    "Natural Honey",
    "Dry Fruits",
    "Organic Spices",
    "Healthy Snacks"
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    // Section Title
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Text(
          text = "Fresh From Nature",
          style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
          color = DeepTextGreen
        )
        Text(
          text = "Carefully selected organic & wholesome essentials",
          style = MaterialTheme.typography.bodyMedium,
          color = LeafGreen
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Search and Sort Control Bar
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      // Search Bar
      OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchChange,
        placeholder = {
          Text(
            text = "Search mango, honey, oil...",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp)
          )
        },
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = ForestGreen
          )
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { onSearchChange("") }) {
              Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear search",
                tint = ForestGreen
              )
            }
          }
        },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = ForestGreen,
          unfocusedBorderColor = EditorialBorder,
          focusedContainerColor = SurfaceCard,
          unfocusedContainerColor = SurfaceCard
        ),
        singleLine = true,
        modifier = Modifier
          .weight(1f)
          .testTag("product_search_input")
      )

      // Sort Menu Button
      Box {
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = WarmOffWhite,
          modifier = Modifier
            .clickable { sortMenuExpanded = true }
            .testTag("sort_filter_button")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 14.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Sort,
              contentDescription = "Sort Options",
              tint = ForestGreen,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Sort",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
              color = ForestGreen
            )
          }
        }

        DropdownMenu(
          expanded = sortMenuExpanded,
          onDismissRequest = { sortMenuExpanded = false }
        ) {
          SortOption.values().forEach { option ->
            DropdownMenuItem(
              text = {
                Text(
                  text = option.label,
                  fontWeight = if (selectedSort == option) FontWeight.Bold else FontWeight.Normal,
                  color = if (selectedSort == option) ForestGreen else DeepTextGreen
                )
              },
              onClick = {
                onSortSelect(option)
                sortMenuExpanded = false
              }
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Category Filter Chips
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      filterCategories.forEach { cat ->
        val isSelected = selectedCategory.equals(cat, ignoreCase = true)
        FilterChip(
          selected = isSelected,
          onClick = { onCategorySelect(cat) },
          label = {
            Text(
              text = cat,
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 11.sp
              )
            )
          },
          shape = RoundedCornerShape(20.dp),
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = ForestGreen,
            selectedLabelColor = SoftCream,
            containerColor = SurfaceCard,
            labelColor = DeepTextGreen
          ),
          border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
          modifier = Modifier.testTag("filter_chip_$cat")
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Demo notice badge
    Surface(
      shape = RoundedCornerShape(10.dp),
      color = SoftSage.copy(alpha = 0.6f),
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = "🌿 All prices in Bangladeshi Taka (৳). Demo & sample catalog for Digital Online Shop BD.",
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
        color = DeepTextGreen.copy(alpha = 0.8f),
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Products Grid
    if (products.isEmpty()) {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = WarmOffWhite),
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 24.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "No matching organic products found",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = DeepTextGreen
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Try clearing filters or searching for another natural product.",
            style = MaterialTheme.typography.bodySmall,
            color = DeepTextGreen.copy(alpha = 0.7f)
          )
          Spacer(modifier = Modifier.height(12.dp))
          Button(
            onClick = {
              onSearchChange("")
              onCategorySelect("All")
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
          ) {
            Text("Reset Filters")
          }
        }
      }
    } else {
      Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        products.chunked(2).forEach { pair ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            pair.forEach { product ->
              ProductCard(
                product = product,
                isWishlisted = wishlistProductIds.contains(product.id),
                onClick = { onProductClick(product) },
                onAddToCart = { onAddToCart(product) },
                onToggleWishlist = { onToggleWishlist(product) },
                modifier = Modifier.weight(1f)
              )
            }
            if (pair.size == 1) {
              Spacer(modifier = Modifier.weight(1f))
            }
          }
        }
      }
    }
  }
}

@Composable
fun ProductCard(
  product: Product,
  isWishlisted: Boolean,
  onClick: () -> Unit,
  onAddToCart: () -> Unit,
  onToggleWishlist: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(28.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
    border = androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = modifier
      .clickable(onClick = onClick)
      .testTag("product_card_${product.id}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      // Product Image Container with Badges & Wishlist heart
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1f)
          .clip(RoundedCornerShape(20.dp))
          .background(WarmOffWhite)
      ) {
        Image(
          painter = painterResource(id = product.imageRes),
          contentDescription = product.name,
          modifier = Modifier.fillMaxWidth(),
          contentScale = ContentScale.Crop
        )

        // Discount / Special Badge
        if (product.discountPercent != null) {
          Box(
            modifier = Modifier
              .align(Alignment.TopStart)
              .padding(8.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(DiscountRed)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "${product.discountPercent}% OFF",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              ),
              color = Color.White
            )
          }
        }

        // Wishlist Toggle Heart Button
        Surface(
          shape = CircleShape,
          color = Color.White.copy(alpha = 0.9f),
          shadowElevation = 1.dp,
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(6.dp)
            .size(32.dp)
            .clickable(onClick = onToggleWishlist)
            .testTag("wishlist_toggle_${product.id}")
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
              contentDescription = if (isWishlisted) "Remove from Wishlist" else "Save to Wishlist",
              tint = if (isWishlisted) DiscountRed else ForestGreen,
              modifier = Modifier.size(16.dp)
            )
          }
        }

        // Unit badge at bottom
        Box(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(6.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(ForestGreenDark.copy(alpha = 0.88f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = product.unit,
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 9.sp,
              fontWeight = FontWeight.Medium
            ),
            color = Color.White
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Rating row
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Star,
          contentDescription = null,
          tint = StarGold,
          modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
          text = "${product.rating} (${product.reviewCount})",
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
          color = DeepTextGreen.copy(alpha = 0.65f)
        )
      }

      Spacer(modifier = Modifier.height(4.dp))

      // Product Name
      Text(
        text = product.name,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp,
          lineHeight = 18.sp
        ),
        color = DeepTextGreen,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      // Short Details / Origin
      Text(
        text = "${product.unit} • 100% Pure Raw",
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 10.sp,
          lineHeight = 13.sp
        ),
        color = DeepTextGreen.copy(alpha = 0.6f),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Price Row (Bangladeshi Taka ৳) & Circular Add Button
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          if (product.prevPrice != null) {
            Text(
              text = "৳${product.prevPrice}",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 10.sp,
                textDecoration = TextDecoration.LineThrough
              ),
              color = DeepTextGreen.copy(alpha = 0.45f)
            )
          }
          Text(
            text = "৳${product.price}",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            ),
            color = ForestGreen
          )
        }

        // Circular Action Button (Editorial Style +)
        Surface(
          shape = CircleShape,
          color = ForestGreen,
          shadowElevation = 2.dp,
          modifier = Modifier
            .size(36.dp)
            .clickable(onClick = onAddToCart)
            .testTag("add_to_cart_${product.id}")
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = Icons.Default.AddShoppingCart,
              contentDescription = "Add to Cart",
              tint = Color.White,
              modifier = Modifier.size(17.dp)
            )
          }
        }
      }
    }
  }
}
