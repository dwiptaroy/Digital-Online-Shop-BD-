package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.SoftCream
import com.example.ui.theme.WarmOffWhite

@Composable
fun SupportDialog(
  title: String,
  onDismiss: () -> Unit
) {
  val content = when (title) {
    "Frequently Asked Questions" -> listOf(
      "How do you source your organic products?" to "We partner directly with smallholder ethical farmers and local beekeepers in Sundarbans, Sylhet, and Rajshahi.",
      "How long does delivery take?" to "Within Dhaka City, orders arrive in 24 hours. Outside Dhaka, eco-delivery takes 48–72 hours.",
      "Are all items 100% natural?" to "Yes, our selection focuses strictly on unrefined, chemical-free and preservative-free whole foods.",
      "Can I pay Cash on Delivery?" to "Yes, Cash on Delivery is available across all 64 districts in Bangladesh."
    )
    "Shipping & Delivery Policy" -> listOf(
      "Standard Delivery Rates" to "Inside Dhaka: ৳60. Nationwide Bangladesh: ৳120. Free shipping for all orders over ৳1,500.",
      "Packaging" to "All items are packed in breathable eco-friendly corrugated boxes and glass jars for maximum freshness.",
      "Tracking" to "You will receive an SMS and email notification with your order details."
    )
    "Returns & Refund Policy" -> listOf(
      "Freshness Guarantee" to "If any perishable item arrives damaged or compromised, we provide an immediate replacement or full refund within 48 hours.",
      "Return Procedure" to "Simply contact our support team with your Order ID."
    )
    else -> listOf(
      "Privacy & Terms Notice" to "Digital Online Shop BD respects customer privacy and protects all personal information. Sample & Demo store for organic e-commerce."
    )
  }

  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = SoftCream,
    shape = RoundedCornerShape(24.dp),
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = DeepTextGreen,
          modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onDismiss) {
          Icon(Icons.Default.Close, contentDescription = "Close", tint = DeepTextGreen)
        }
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        content.forEach { (q, a) ->
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = WarmOffWhite),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = q,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = ForestGreen
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = a,
                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
                color = DeepTextGreen.copy(alpha = 0.85f)
              )
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
        modifier = Modifier.fillMaxWidth()
      ) {
        Text("Got It")
      }
    }
  )
}
