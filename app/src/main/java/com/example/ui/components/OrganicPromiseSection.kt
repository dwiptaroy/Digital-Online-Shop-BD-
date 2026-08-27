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
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.Visibility
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
import com.example.ui.theme.EarthyBrown
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.MutedYellow
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite

@Composable
fun OrganicPromiseSection(
  modifier: Modifier = Modifier
) {
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
          .padding(horizontal = 10.dp, vertical = 5.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Nature,
          contentDescription = null,
          tint = ForestGreen,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Our Core Philosophy",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = ForestGreen
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "Our Organic Promise",
        style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp
        ),
        color = DeepTextGreen
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "“We believe good food starts with thoughtful choices. Our goal is to make natural, wholesome products easier to discover while promoting a cleaner and more conscious lifestyle.”",
        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 23.sp, fontSize = 14.sp),
        color = DeepTextGreen.copy(alpha = 0.9f)
      )

      Spacer(modifier = Modifier.height(18.dp))

      // 3 Visual Principles
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        PrincipleCard(
          title = "Natural",
          subtitle = "Minimally processed pure harvest",
          icon = Icons.Default.Nature,
          accentColor = ForestGreen,
          modifier = Modifier.weight(1f)
        )
        PrincipleCard(
          title = "Responsible",
          subtitle = "Eco-conscious farmer care",
          icon = Icons.Default.Grass,
          accentColor = EarthyBrown,
          modifier = Modifier.weight(1f)
        )
        PrincipleCard(
          title = "Transparent",
          subtitle = "Honest sourcing & honest facts",
          icon = Icons.Default.Visibility,
          accentColor = LeafGreen,
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}

@Composable
private fun PrincipleCard(
  title: String,
  subtitle: String,
  icon: ImageVector,
  accentColor: androidx.compose.ui.graphics.Color,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceCard),
    border = androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder),
    modifier = modifier
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(accentColor.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = accentColor,
          modifier = Modifier.size(18.dp)
        )
      }
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = DeepTextGreen
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 14.sp),
        color = DeepTextGreen.copy(alpha = 0.7f)
      )
    }
  }
}
