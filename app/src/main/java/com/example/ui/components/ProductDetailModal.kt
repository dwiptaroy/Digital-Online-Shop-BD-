package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.DiscountRed
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.MutedYellow
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.StarGold
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailModal(
  product: Product,
  isWishlisted: Boolean,
  onDismiss: () -> Unit,
  onAddToCart: (Product, Int) -> Unit,
  onToggleWishlist: (Product) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var quantity by remember { mutableIntStateOf(1) }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = SurfaceCard,
    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp)
        .padding(bottom = 32.dp)
    ) {
      // Top header with close button & category
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(SoftSage)
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Eco,
            contentDescription = null,
            tint = ForestGreen,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = product.category,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = ForestGreen
          )
        }

        IconButton(onClick = onDismiss) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close details",
            tint = DeepTextGreen
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Product Image
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(16f / 10f)
          .clip(RoundedCornerShape(24.dp))
          .background(WarmOffWhite)
      ) {
        Image(
          painter = painterResource(id = product.imageRes),
          contentDescription = product.name,
          modifier = Modifier.fillMaxWidth(),
          contentScale = ContentScale.Crop
        )

        if (product.discountPercent != null) {
          Box(
            modifier = Modifier
              .align(Alignment.TopStart)
              .padding(12.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(DiscountRed)
              .padding(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Text(
              text = "${product.discountPercent}% OFF",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = SoftCream
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Title and Rating
      Text(
        text = product.name,
        style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 22.sp
        ),
        color = DeepTextGreen
      )

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = StarGold,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "${product.rating} (${product.reviewCount} customer reviews)",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            color = DeepTextGreen
          )
        }

        Text(text = "•", color = DeepTextGreen.copy(alpha = 0.5f))

        Text(
          text = product.unit,
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
          color = LeafGreen
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Price Row
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Text(
          text = "৳${product.price}",
          style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
          color = ForestGreen
        )

        if (product.prevPrice != null) {
          Text(
            text = "৳${product.prevPrice}",
            style = MaterialTheme.typography.titleMedium.copy(
              textDecoration = TextDecoration.LineThrough
            ),
            color = DeepTextGreen.copy(alpha = 0.5f)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Origin badge
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .clip(RoundedCornerShape(14.dp))
          .background(WarmOffWhite)
          .border(1.dp, EditorialBorder, RoundedCornerShape(14.dp))
          .padding(horizontal = 12.dp, vertical = 8.dp)
      ) {
        Icon(
          imageVector = Icons.Default.LocationOn,
          contentDescription = null,
          tint = ForestGreen,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Origin: ${product.origin}",
          style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
          color = DeepTextGreen
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Description
      Text(
        text = "Product Details",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = DeepTextGreen
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = product.fullDescription,
        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
        color = DeepTextGreen.copy(alpha = 0.85f)
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Highlights
      Text(
        text = "Wholesome Highlights",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = DeepTextGreen
      )
      Spacer(modifier = Modifier.height(6.dp))
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        product.highlights.forEach { item ->
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Eco,
              contentDescription = null,
              tint = LeafGreen,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = item,
              style = MaterialTheme.typography.bodySmall,
              color = DeepTextGreen
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Quantity selector and Add to Cart
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Quantity Controls
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = WarmOffWhite,
          border = androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
          modifier = Modifier.height(48.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp)
          ) {
            IconButton(
              onClick = { if (quantity > 1) quantity-- },
              modifier = Modifier.size(36.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease",
                tint = ForestGreen,
                modifier = Modifier.size(16.dp)
              )
            }

            Text(
              text = quantity.toString(),
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = DeepTextGreen,
              modifier = Modifier.padding(horizontal = 8.dp)
            )

            IconButton(
              onClick = { quantity++ },
              modifier = Modifier.size(36.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase",
                tint = ForestGreen,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }

        // Add to Cart Button
        Button(
          onClick = {
            onAddToCart(product, quantity)
            onDismiss()
          },
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = ForestGreen,
            contentColor = SoftCream
          ),
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("modal_add_to_cart_btn")
        ) {
          Icon(
            imageVector = Icons.Default.ShoppingBag,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Add (৳${product.price * quantity})",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
          )
        }

        // Wishlist Button
        IconButton(
          onClick = { onToggleWishlist(product) },
          modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(WarmOffWhite)
            .border(1.dp, EditorialBorder, RoundedCornerShape(16.dp))
        ) {
          Icon(
            imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Toggle Wishlist",
            tint = if (isWishlisted) DiscountRed else ForestGreen
          )
        }
      }
    }
  }
}
