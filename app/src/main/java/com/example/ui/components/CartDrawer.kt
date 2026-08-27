package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import com.example.data.model.CartItemEntity
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.DiscountRed
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartDrawer(
  cartItems: List<CartItemEntity>,
  onClose: () -> Unit,
  onUpdateQuantity: (String, Int) -> Unit,
  onRemoveItem: (String) -> Unit,
  onProceedToCheckout: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  val subtotal = cartItems.sumOf { it.price * it.quantity }
  val freeDeliveryThreshold = 1500
  val deliveryFee = if (subtotal >= freeDeliveryThreshold || subtotal == 0) 0 else 60
  val total = subtotal + deliveryFee
  val progress = if (freeDeliveryThreshold > 0) {
    (subtotal.toFloat() / freeDeliveryThreshold).coerceIn(0f, 1f)
  } else 1f

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
        .padding(bottom = 24.dp)
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.ShoppingBag,
            contentDescription = null,
            tint = ForestGreen,
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Shopping Cart (${cartItems.sumOf { it.quantity }})",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = DeepTextGreen
          )
        }

        IconButton(onClick = onClose) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close cart",
            tint = DeepTextGreen
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Free Delivery Progress Banner
      if (cartItems.isNotEmpty()) {
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = SoftSage,
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.LocalShipping,
                contentDescription = null,
                tint = ForestGreen,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (subtotal >= freeDeliveryThreshold) {
                  "🎉 You have unlocked Free Organic Delivery!"
                } else {
                  "Add ৳${freeDeliveryThreshold - subtotal} more for Free Delivery!"
                },
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = ForestGreen
              )
            }
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
              progress = { progress },
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
              color = ForestGreen,
              trackColor = SurfaceCard
            )
          }
        }
        Spacer(modifier = Modifier.height(12.dp))
      }

      if (cartItems.isEmpty()) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(70.dp)
              .clip(CircleShape)
              .background(WarmOffWhite),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Outlined.ShoppingBag,
              contentDescription = null,
              tint = LeafGreen,
              modifier = Modifier.size(36.dp)
            )
          }
          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "Your cart is currently empty",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = DeepTextGreen
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Explore our organic fruits, natural honey, and cold-pressed oils.",
            style = MaterialTheme.typography.bodySmall,
            color = DeepTextGreen.copy(alpha = 0.7f)
          )
          Spacer(modifier = Modifier.height(20.dp))
          Button(
            onClick = onClose,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
          ) {
            Text("Start Shopping")
          }
        }
      } else {
        // Items list
        LazyColumn(
          modifier = Modifier
            .weight(1f, fill = false)
            .height(240.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(cartItems, key = { it.productId }) { item ->
            CartItemRow(
              item = item,
              onIncrease = { onUpdateQuantity(item.productId, 1) },
              onDecrease = { onUpdateQuantity(item.productId, -1) },
              onRemove = { onRemoveItem(item.productId) }
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider(color = EditorialBorder)
        Spacer(modifier = Modifier.height(12.dp))

        // Price summary
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Subtotal",
            style = MaterialTheme.typography.bodyMedium,
            color = DeepTextGreen.copy(alpha = 0.8f)
          )
          Text(
            text = "৳$subtotal",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = DeepTextGreen
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Estimated Delivery",
            style = MaterialTheme.typography.bodyMedium,
            color = DeepTextGreen.copy(alpha = 0.8f)
          )
          Text(
            text = if (deliveryFee == 0) "FREE" else "৳$deliveryFee",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = if (deliveryFee == 0) ForestGreen else DeepTextGreen
            )
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Total Amount",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = DeepTextGreen
          )
          Text(
            text = "৳$total",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = ForestGreen
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = onClose,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
          ) {
            Text("Continue Shopping", style = MaterialTheme.typography.labelMedium)
          }

          Button(
            onClick = onProceedToCheckout,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
              .testTag("proceed_to_checkout_btn")
          ) {
            Text(
              text = "Checkout",
              style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun CartItemRow(
  item: CartItemEntity,
  onIncrease: () -> Unit,
  onDecrease: () -> Unit,
  onRemove: () -> Unit
) {
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
          text = "৳${item.price} × ${item.quantity} = ৳${item.price * item.quantity}",
          style = MaterialTheme.typography.bodySmall.copy(
            color = ForestGreen,
            fontWeight = FontWeight.SemiBold
          )
        )
      }

      // Stepper
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(SurfaceCard)
          .padding(horizontal = 4.dp, vertical = 2.dp)
      ) {
        IconButton(
          onClick = onDecrease,
          modifier = Modifier.size(28.dp)
        ) {
          Icon(
            imageVector = if (item.quantity == 1) Icons.Default.DeleteOutline else Icons.Default.Remove,
            contentDescription = "Decrease",
            tint = if (item.quantity == 1) DiscountRed else ForestGreen,
            modifier = Modifier.size(14.dp)
          )
        }

        Text(
          text = item.quantity.toString(),
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
          color = DeepTextGreen,
          modifier = Modifier.padding(horizontal = 6.dp)
        )

        IconButton(
          onClick = onIncrease,
          modifier = Modifier.size(28.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Increase",
            tint = ForestGreen,
            modifier = Modifier.size(14.dp)
          )
        }
      }
    }
  }
}
