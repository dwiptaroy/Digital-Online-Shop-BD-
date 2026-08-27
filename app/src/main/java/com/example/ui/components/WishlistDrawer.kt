package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.WishlistItemEntity
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.DiscountRed
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistDrawer(
  wishlistItems: List<WishlistItemEntity>,
  onClose: () -> Unit,
  onMoveToCart: (WishlistItemEntity) -> Unit,
  onRemoveItem: (String) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onClose,
    sheetState = sheetState,
    containerColor = SurfaceCard,
    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(bottom = 28.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = DiscountRed,
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Saved Favorites (${wishlistItems.size})",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = DeepTextGreen
          )
        }

        IconButton(onClick = onClose) {
          Icon(Icons.Default.Close, contentDescription = "Close wishlist", tint = DeepTextGreen)
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      if (wishlistItems.isEmpty()) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(68.dp)
              .clip(CircleShape)
              .background(WarmOffWhite),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Outlined.FavoriteBorder,
              contentDescription = null,
              tint = LeafGreen,
              modifier = Modifier.size(36.dp)
            )
          }
          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "Your wishlist is empty",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = DeepTextGreen
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Click the heart icon on any product to save it here for later.",
            style = MaterialTheme.typography.bodySmall,
            color = DeepTextGreen.copy(alpha = 0.7f)
          )
          Spacer(modifier = Modifier.height(20.dp))
          Button(
            onClick = onClose,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
          ) {
            Text("Explore Products")
          }
        }
      } else {
        LazyColumn(
          modifier = Modifier
            .weight(1f, fill = false)
            .height(280.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(wishlistItems, key = { it.productId }) { item ->
            Card(
              shape = RoundedCornerShape(18.dp),
              colors = CardDefaults.cardColors(containerColor = WarmOffWhite),
              border = androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceCard)
                ) {
                  Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                  )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = DeepTextGreen,
                    maxLines = 1
                  )
                  Text(
                    text = "৳${item.price} • ${item.unit}",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = ForestGreen,
                      fontWeight = FontWeight.SemiBold
                    )
                  )
                }

                // Move to Cart Button
                Button(
                  onClick = { onMoveToCart(item) },
                  shape = RoundedCornerShape(12.dp),
                  colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
                  contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                  modifier = Modifier.height(34.dp)
                ) {
                  Icon(Icons.Default.AddShoppingCart, contentDescription = null, modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Add", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                }

                Spacer(modifier = Modifier.width(4.dp))

                IconButton(
                  onClick = { onRemoveItem(item.productId) },
                  modifier = Modifier.size(32.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Remove",
                    tint = DiscountRed,
                    modifier = Modifier.size(18.dp)
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
