package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.OrderSubmission
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.WarmOffWhite

@Composable
fun OrderSuccessDialog(
  order: OrderSubmission,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = SoftCream,
    shape = RoundedCornerShape(24.dp),
    title = null,
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(SoftSage),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Order Confirmed",
            tint = ForestGreen,
            modifier = Modifier.size(36.dp)
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Thank You For Choosing Organic!",
          style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
          color = DeepTextGreen
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "Your demo order has been placed successfully at Digital Online Shop BD.",
          style = MaterialTheme.typography.bodySmall,
          color = DeepTextGreen.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = WarmOffWhite),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
              Text("Order ID:", style = MaterialTheme.typography.bodySmall)
              Text(
                order.orderId,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = ForestGreen
              )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
              Text("Customer:", style = MaterialTheme.typography.bodySmall)
              Text(order.customerName, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
              Text("Destination:", style = MaterialTheme.typography.bodySmall)
              Text("${order.deliveryCity}, BD", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
              Text("Payment:", style = MaterialTheme.typography.bodySmall)
              Text(order.paymentMethod, style = MaterialTheme.typography.bodySmall)
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = SoftSage)

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text("Total Amount:", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
              Text("৳${order.totalAmount}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = ForestGreen)
            }
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = onDismiss,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
        modifier = Modifier.fillMaxWidth().testTag("order_success_continue_button")
      ) {
        Text("Continue Exploring", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
      }
    }
  )
}
