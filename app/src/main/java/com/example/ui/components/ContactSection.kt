package com.example.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.DiscountRed
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite

@Composable
fun ContactSection(
  onSubmitContact: (name: String, email: String, phone: String, message: String) -> Boolean,
  modifier: Modifier = Modifier
) {
  var name by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var message by remember { mutableStateOf("") }
  var errorMessage by remember { mutableStateOf<String?>(null) }
  var isSent by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 10.dp)
  ) {
    Text(
      text = "Contact Us",
      style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
      color = LeafGreen
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "We’d Love To Hear From You",
      style = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
      ),
      color = DeepTextGreen
    )
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = "Reach out with product inquiries, wholesale orders, or organic farming feedback.",
      style = MaterialTheme.typography.bodyMedium,
      color = DeepTextGreen.copy(alpha = 0.8f)
    )

    Spacer(modifier = Modifier.height(14.dp))

    // Business Info Cards
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      ContactInfoCard(
        icon = Icons.Default.Phone,
        label = "Phone",
        value = "+880 1XXXXXXXXX",
        modifier = Modifier.weight(1f)
      )
      ContactInfoCard(
        icon = Icons.Default.Email,
        label = "Email",
        value = "hello@example.com",
        modifier = Modifier.weight(1f)
      )
      ContactInfoCard(
        icon = Icons.Default.LocationOn,
        label = "Address",
        value = "Bangladesh",
        modifier = Modifier.weight(1f)
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Contact Form Card
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = SurfaceCard),
      border = androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
      elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        if (isSent) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(16.dp))
              .background(SoftSage)
              .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.Email,
              contentDescription = null,
              tint = ForestGreen,
              modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Message Sent Successfully!",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = ForestGreen
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Thank you for reaching out. Our support team will get back to you shortly.",
              style = MaterialTheme.typography.bodySmall,
              color = DeepTextGreen
            )
          }
        } else {
          Text(
            text = "Send Us a Message",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = DeepTextGreen
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Name
          OutlinedTextField(
            value = name,
            onValueChange = { name = it; errorMessage = null },
            label = { Text("Your Name") },
            leadingIcon = {
              Icon(Icons.Default.Person, contentDescription = null, tint = ForestGreen)
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = ForestGreen,
              unfocusedBorderColor = EditorialBorder,
              focusedContainerColor = WarmOffWhite,
              unfocusedContainerColor = WarmOffWhite
            ),
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("contact_name_input")
          )

          Spacer(modifier = Modifier.height(8.dp))

          // Email
          OutlinedTextField(
            value = email,
            onValueChange = { email = it; errorMessage = null },
            label = { Text("Email Address") },
            leadingIcon = {
              Icon(Icons.Default.Email, contentDescription = null, tint = ForestGreen)
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = ForestGreen,
              unfocusedBorderColor = EditorialBorder,
              focusedContainerColor = WarmOffWhite,
              unfocusedContainerColor = WarmOffWhite
            ),
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("contact_email_input")
          )

          Spacer(modifier = Modifier.height(8.dp))

          // Phone
          OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number (+880...)") },
            leadingIcon = {
              Icon(Icons.Default.Phone, contentDescription = null, tint = ForestGreen)
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = ForestGreen,
              unfocusedBorderColor = EditorialBorder,
              focusedContainerColor = WarmOffWhite,
              unfocusedContainerColor = WarmOffWhite
            ),
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("contact_phone_input")
          )

          Spacer(modifier = Modifier.height(8.dp))

          // Message
          OutlinedTextField(
            value = message,
            onValueChange = { message = it; errorMessage = null },
            label = { Text("Your Message / Requirement") },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = ForestGreen,
              unfocusedBorderColor = EditorialBorder,
              focusedContainerColor = WarmOffWhite,
              unfocusedContainerColor = WarmOffWhite
            ),
            minLines = 3,
            maxLines = 5,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("contact_message_input")
          )

          if (errorMessage != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = errorMessage ?: "",
              color = DiscountRed,
              style = MaterialTheme.typography.bodySmall
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = {
              if (name.isBlank() || !email.contains("@") || message.isBlank()) {
                errorMessage = "Please fill in your name, valid email, and message."
              } else {
                if (onSubmitContact(name, email, phone, message)) {
                  isSent = true
                }
              }
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = ForestGreen,
              contentColor = SoftCream
            ),
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("contact_submit_button")
          ) {
            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Send Message",
              style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun ContactInfoCard(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  value: String,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = SurfaceCard,
    border = androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
    modifier = modifier
  ) {
    Column(
      modifier = Modifier.padding(10.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .size(28.dp)
          .clip(CircleShape)
          .background(SoftSage),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = ForestGreen,
          modifier = Modifier.size(14.dp)
        )
      }
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
        color = ForestGreen
      )
      Text(
        text = value,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
        color = DeepTextGreen.copy(alpha = 0.8f),
        maxLines = 1
      )
    }
  }
}
