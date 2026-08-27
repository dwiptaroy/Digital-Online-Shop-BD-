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
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite

data class WhyChooseItem(
  val number: String,
  val title: String,
  val description: String,
  val icon: ImageVector
)

@Composable
fun WhyChooseUsSection(
  modifier: Modifier = Modifier
) {
  val items = listOf(
    WhyChooseItem(
      number = "01",
      title = "Carefully Selected",
      description = "Products selected with quality and freshness in mind.",
      icon = Icons.Default.Spa
    ),
    WhyChooseItem(
      number = "02",
      title = "Natural Choices",
      description = "Discover everyday food options inspired by a simpler lifestyle.",
      icon = Icons.Default.Eco
    ),
    WhyChooseItem(
      number = "03",
      title = "Quality Focused",
      description = "We prioritize consistency, presentation and customer experience.",
      icon = Icons.Default.Diamond
    ),
    WhyChooseItem(
      number = "04",
      title = "Customer First",
      description = "A simple and convenient shopping experience designed around you.",
      icon = Icons.Default.Favorite
    )
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 10.dp)
  ) {
    // Header
    Text(
      text = "Why Choose Digital Online Shop BD?",
      style = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
      ),
      color = DeepTextGreen
    )
    Text(
      text = "Rooted in nature, crafted with integrity and trust.",
      style = MaterialTheme.typography.bodyMedium,
      color = LeafGreen
    )

    Spacer(modifier = Modifier.height(14.dp))

    // 4 feature cards
    Column(
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items.forEach { item ->
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = SurfaceCard),
          border = androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalAlignment = Alignment.Top
          ) {
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(SoftSage),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = ForestGreen,
                modifier = Modifier.size(22.dp)
              )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  text = item.title,
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  color = DeepTextGreen
                )
                Text(
                  text = item.number,
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                  color = LeafGreen
                )
              }

              Spacer(modifier = Modifier.height(4.dp))

              Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 19.sp),
                color = DeepTextGreen.copy(alpha = 0.75f)
              )
            }
          }
        }
      }
    }
  }
}
