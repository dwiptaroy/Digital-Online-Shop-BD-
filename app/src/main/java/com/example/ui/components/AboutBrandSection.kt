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
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.EarthyBrown
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite

@Composable
fun AboutBrandSection(
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 10.dp)
  ) {
    Text(
      text = "About Digital Online Shop BD",
      style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
      color = LeafGreen
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = "Better Choices Begin With Better Food.",
      style = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
      ),
      color = DeepTextGreen
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "Digital Online Shop BD is an organic food and natural products brand focused on bringing carefully selected products to customers through a simple and convenient online shopping experience.",
      style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 22.sp, fontSize = 14.sp),
      color = DeepTextGreen.copy(alpha = 0.8f)
    )

    Spacer(modifier = Modifier.height(14.dp))

    // Mission & Vision Cards
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Mission Card
      Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.weight(1f)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(ForestGreen.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Flag,
              contentDescription = "Mission",
              tint = ForestGreen,
              modifier = Modifier.size(18.dp)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Our Mission",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = ForestGreen
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "To make natural and quality-focused food products easier to discover and access across all regions.",
            style = MaterialTheme.typography.bodySmall.copy(lineHeight = 17.sp),
            color = DeepTextGreen.copy(alpha = 0.75f)
          )
        }
      }

      // Vision Card
      Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.weight(1f)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(EarthyBrown.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Lightbulb,
              contentDescription = "Vision",
              tint = EarthyBrown,
              modifier = Modifier.size(18.dp)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Our Vision",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = EarthyBrown
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "To build a trusted digital destination for conscious food choices and healthy lifestyle in Bangladesh.",
            style = MaterialTheme.typography.bodySmall.copy(lineHeight = 17.sp),
            color = DeepTextGreen.copy(alpha = 0.75f)
          )
        }
      }
    }
  }
}
