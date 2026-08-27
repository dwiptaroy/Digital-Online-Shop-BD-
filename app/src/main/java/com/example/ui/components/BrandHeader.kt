package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.MutedYellow
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite
import com.example.ui.viewmodel.ActiveTab

@Composable
fun BrandHeader(
  activeTab: ActiveTab,
  cartCount: Int,
  wishlistCount: Int,
  onTabSelected: (ActiveTab) -> Unit,
  onOpenSearch: () -> Unit,
  onOpenCart: () -> Unit,
  onOpenWishlist: () -> Unit,
  onOpenDrawer: () -> Unit,
  onShopOrganicClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    color = WarmOffWhite
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 10.dp)
    ) {
      // Top bar row: Welcome Subtitle, Brand Name, and Circular Action Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        // Left: Hamburger Menu & Brand Identity
        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          Surface(
            shape = CircleShape,
            color = SurfaceCard,
            shadowElevation = 1.dp,
            modifier = Modifier
              .size(40.dp)
              .clickable(onClick = onOpenDrawer)
              .testTag("menu_drawer_button")
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Open Navigation Menu",
                tint = ForestGreen,
                modifier = Modifier.size(20.dp)
              )
            }
          }

          Spacer(modifier = Modifier.width(10.dp))

          // Brand Identity Clickable
          Column(
            modifier = Modifier.clickable { onTabSelected(ActiveTab.HOME) }
          ) {
            Text(
              text = "WELCOME TO",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.5.sp
              ),
              color = DeepTextGreen.copy(alpha = 0.6f)
            )
            Text(
              text = "Digital Online Shop BD",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                letterSpacing = (-0.3).sp
              ),
              color = ForestGreen
            )
          }
        }

        // Right Action Icons: Search, Wishlist, Cart in Circular White Floating Pills
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Search Icon
          Surface(
            shape = CircleShape,
            color = SurfaceCard,
            shadowElevation = 1.dp,
            modifier = Modifier
              .size(40.dp)
              .clickable(onClick = onOpenSearch)
              .testTag("header_search_button")
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Organic Products",
                tint = DeepTextGreen,
                modifier = Modifier.size(20.dp)
              )
            }
          }

          // Wishlist Icon
          Surface(
            shape = CircleShape,
            color = SurfaceCard,
            shadowElevation = 1.dp,
            modifier = Modifier
              .size(40.dp)
              .clickable(onClick = onOpenWishlist)
              .testTag("header_wishlist_button")
          ) {
            Box(contentAlignment = Alignment.Center) {
              BadgedBox(
                badge = {
                  if (wishlistCount > 0) {
                    Badge(
                      containerColor = MutedYellow,
                      contentColor = ForestGreen
                    ) {
                      Text(
                        text = wishlistCount.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                          fontWeight = FontWeight.Bold,
                          fontSize = 9.sp
                        )
                      )
                    }
                  }
                }
              ) {
                Icon(
                  imageVector = if (wishlistCount > 0) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                  contentDescription = "Saved Wishlist",
                  tint = if (wishlistCount > 0) ForestGreen else DeepTextGreen,
                  modifier = Modifier.size(20.dp)
                )
              }
            }
          }

          // Shopping Cart Icon
          Surface(
            shape = CircleShape,
            color = SurfaceCard,
            shadowElevation = 1.dp,
            modifier = Modifier
              .size(40.dp)
              .clickable(onClick = onOpenCart)
              .testTag("header_cart_button")
          ) {
            Box(contentAlignment = Alignment.Center) {
              BadgedBox(
                badge = {
                  if (cartCount > 0) {
                    Badge(
                      containerColor = ForestGreen,
                      contentColor = SoftCream
                    ) {
                      Text(
                        text = cartCount.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                          fontWeight = FontWeight.Bold,
                          fontSize = 9.sp
                        )
                      )
                    }
                  }
                }
              ) {
                Icon(
                  imageVector = Icons.Outlined.ShoppingCart,
                  contentDescription = "Shopping Cart",
                  tint = ForestGreen,
                  modifier = Modifier.size(20.dp)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Navigation Bar Category Chips & CTA Button
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        ActiveTab.values().forEach { tab ->
          val isSelected = activeTab == tab
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (isSelected) ForestGreen else SurfaceCard,
            shadowElevation = if (isSelected) 2.dp else 0.5.dp,
            modifier = Modifier
              .clickable { onTabSelected(tab) }
          ) {
            Text(
              text = tab.label,
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 12.sp
              ),
              color = if (isSelected) SoftCream else DeepTextGreen,
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
            )
          }
        }

        Spacer(modifier = Modifier.width(4.dp))

        // CTA "Shop Organic"
        Button(
          onClick = onShopOrganicClick,
          shape = RoundedCornerShape(20.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = LeafGreen,
            contentColor = SoftCream
          ),
          modifier = Modifier
            .height(34.dp)
            .testTag("shop_organic_header_button")
        ) {
          Icon(
            imageVector = Icons.Default.ShoppingBag,
            contentDescription = null,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Shop Organic",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )
        }
      }
    }
  }
}
