package com.example.ui.components

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CartItemEntity
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.DiscountRed
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.MutedYellow
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutModal(
  cartItems: List<CartItemEntity>,
  onClose: () -> Unit,
  onPlaceOrder: (name: String, phone: String, email: String, address: String, city: String, payment: String, total: Int, count: Int) -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  var name by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var address by remember { mutableStateOf("") }
  var city by remember { mutableStateOf("Dhaka") }
  var selectedPayment by remember { mutableStateOf("Cash on Delivery") }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  val subtotal = cartItems.sumOf { it.price * it.quantity }
  val deliveryFee = if (subtotal >= 1500) 0 else if (city == "Dhaka") 60 else 120
  val total = subtotal + deliveryFee
  val totalItemCount = cartItems.sumOf { it.quantity }

  ModalBottomSheet(
    onDismissRequest = onClose,
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
            text = "Checkout & Delivery",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = DeepTextGreen
          )
        }

        IconButton(onClick = onClose) {
          Icon(Icons.Default.Close, contentDescription = "Close checkout", tint = DeepTextGreen)
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Delivery Notice
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = SoftSage,
        border = androidx.compose.foundation.BorderStroke(1.dp, ForestGreen.copy(alpha = 0.15f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.LocalShipping, contentDescription = null, tint = ForestGreen, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Fast Eco-Delivery across Bangladesh. Cash on Delivery supported.",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, fontWeight = FontWeight.SemiBold),
            color = ForestGreen
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "1. Customer Information",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = DeepTextGreen
      )

      Spacer(modifier = Modifier.height(10.dp))

      OutlinedTextField(
        value = name,
        onValueChange = { name = it; errorMessage = null },
        label = { Text("Full Name *") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = ForestGreen) },
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = ForestGreen,
          unfocusedBorderColor = EditorialBorder,
          focusedContainerColor = WarmOffWhite,
          unfocusedContainerColor = WarmOffWhite
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth().testTag("checkout_name_input")
      )

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = phone,
        onValueChange = { phone = it; errorMessage = null },
        label = { Text("Phone Number (+8801XXXXXXXXX) *") },
        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = ForestGreen) },
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = ForestGreen,
          unfocusedBorderColor = EditorialBorder,
          focusedContainerColor = WarmOffWhite,
          unfocusedContainerColor = WarmOffWhite
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth().testTag("checkout_phone_input")
      )

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Email (Optional for receipt)") },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = ForestGreen) },
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = ForestGreen,
          unfocusedBorderColor = EditorialBorder,
          focusedContainerColor = WarmOffWhite,
          unfocusedContainerColor = WarmOffWhite
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
      )

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "2. Delivery Location",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = DeepTextGreen
      )

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        listOf("Dhaka", "Chattogram", "Sylhet", "Rajshahi", "Other").forEach { cityName ->
          val isSelected = city == cityName
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(12.dp))
              .background(if (isSelected) ForestGreen else WarmOffWhite)
              .clickable { city = cityName }
              .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = cityName,
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = if (isSelected) SoftCream else DeepTextGreen
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = address,
        onValueChange = { address = it; errorMessage = null },
        label = { Text("Full Street Address / House / Road *") },
        leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = ForestGreen) },
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = ForestGreen,
          unfocusedBorderColor = EditorialBorder,
          focusedContainerColor = WarmOffWhite,
          unfocusedContainerColor = WarmOffWhite
        ),
        minLines = 2,
        modifier = Modifier.fillMaxWidth().testTag("checkout_address_input")
      )

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "3. Payment Method",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = DeepTextGreen
      )

      Spacer(modifier = Modifier.height(8.dp))

      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        listOf(
          "Cash on Delivery" to "Pay cash when organic package arrives",
          "bKash (Demo Simulation)" to "Pay via bKash mobile banking wallet",
          "Nagad (Demo Simulation)" to "Pay via Nagad digital payment"
        ).forEach { (method, desc) ->
          val isSelected = selectedPayment == method
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = if (isSelected) SoftSage else WarmOffWhite),
            border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, ForestGreen) else androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
            modifier = Modifier.fillMaxWidth().clickable { selectedPayment = method }
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              RadioButton(
                selected = isSelected,
                onClick = { selectedPayment = method },
                colors = RadioButtonDefaults.colors(selectedColor = ForestGreen)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = method,
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                  color = DeepTextGreen
                )
                Text(
                  text = desc,
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                  color = DeepTextGreen.copy(alpha = 0.7f)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Summary Card
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = WarmOffWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Order Summary ($totalItemCount items)",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = DeepTextGreen
          )
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Subtotal", style = MaterialTheme.typography.bodyMedium)
            Text("৳$subtotal", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Delivery Fee ($city)", style = MaterialTheme.typography.bodyMedium)
            Text(if (deliveryFee == 0) "FREE" else "৳$deliveryFee", style = MaterialTheme.typography.bodyMedium)
          }
          HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = EditorialBorder)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("Total Payable", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Text("৳$total", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), color = ForestGreen)
          }
        }
      }

      if (errorMessage != null) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = errorMessage ?: "",
          color = DiscountRed,
          style = MaterialTheme.typography.bodySmall
        )
      }

      Spacer(modifier = Modifier.height(18.dp))

      Button(
        onClick = {
          if (name.isBlank() || phone.isBlank() || address.isBlank()) {
            errorMessage = "Please enter your name, contact phone, and delivery address."
          } else {
            onPlaceOrder(name, phone, email, address, city, selectedPayment, total, totalItemCount)
          }
        },
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = ForestGreen),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("confirm_order_button")
      ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Confirm & Place Order (৳$total)",
          style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
        )
      }
    }
  }
}
