package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite

@Composable
fun NewsletterSection(
  onSubscribe: (String) -> Boolean,
  modifier: Modifier = Modifier
) {
  var email by remember { mutableStateOf("") }
  var isSubmitted by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  Card(
    shape = RoundedCornerShape(28.dp),
    colors = CardDefaults.cardColors(containerColor = SoftSage),
    border = androidx.compose.foundation.BorderStroke(1.dp, ForestGreen.copy(alpha = 0.15f)),
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 10.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .clip(RoundedCornerShape(30.dp))
          .background(ForestGreen.copy(alpha = 0.12f))
          .padding(horizontal = 10.dp, vertical = 4.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Email,
          contentDescription = null,
          tint = ForestGreen,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Organic Newsletter",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = ForestGreen
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Stay Connected With Nature",
        style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp
        ),
        color = DeepTextGreen
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "Get product updates, seasonal offers and useful food inspiration delivered straight to your inbox.",
        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp, fontSize = 14.sp),
        color = DeepTextGreen.copy(alpha = 0.85f)
      )

      Spacer(modifier = Modifier.height(16.dp))

      if (isSubmitted) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(ForestGreen)
            .padding(14.dp)
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = SoftCream,
            modifier = Modifier.size(22.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "Subscribed Successfully!",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = SoftCream
            )
            Text(
              text = "You will receive periodic natural living tips and sample product news.",
              style = MaterialTheme.typography.bodySmall,
              color = SoftCream.copy(alpha = 0.85f)
            )
          }
        }
      } else {
        Column(modifier = Modifier.fillMaxWidth()) {
          OutlinedTextField(
            value = email,
            onValueChange = {
              email = it
              errorMessage = null
            },
            placeholder = { Text("Enter your email address (e.g. name@domain.com)") },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = ForestGreen
              )
            },
            isError = errorMessage != null,
            supportingText = {
              if (errorMessage != null) {
                Text(
                  text = errorMessage ?: "",
                  color = DiscountRed,
                  style = MaterialTheme.typography.bodySmall
                )
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
              .fillMaxWidth()
              .testTag("newsletter_email_input")
          )

          Spacer(modifier = Modifier.height(8.dp))

          Button(
            onClick = {
              if (onSubscribe(email)) {
                isSubmitted = true
                errorMessage = null
              } else {
                errorMessage = "Please enter a valid email address."
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
              .testTag("newsletter_subscribe_button")
          ) {
            Icon(
              imageVector = Icons.Default.MarkEmailRead,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Subscribe to Newsletter",
              style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }
  }
}
