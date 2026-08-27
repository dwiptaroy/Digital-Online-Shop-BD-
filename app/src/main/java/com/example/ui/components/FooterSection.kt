package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.MutedYellow
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.WarmOffWhite
import com.example.ui.viewmodel.ActiveTab

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FooterSection(
  onNavigateTab: (ActiveTab) -> Unit,
  onOpenSupportInfo: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    color = DeepTextGreen
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
      // Brand Row
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(LeafGreen),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Eco,
            contentDescription = null,
            tint = SoftCream,
            modifier = Modifier.size(18.dp)
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = "Digital Online Shop BD",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = SoftCream
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Wholesome, natural and carefully selected organic food products across Bangladesh. Committed to clean nutrition and customer trust.",
        style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
        color = WarmOffWhite.copy(alpha = 0.8f)
      )

      Spacer(modifier = Modifier.height(20.dp))
      HorizontalDivider(color = ForestGreen.copy(alpha = 0.5f))
      Spacer(modifier = Modifier.height(16.dp))

      // Columns Grid
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        // Quick Links
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Quick Links",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MutedYellow
          )
          Spacer(modifier = Modifier.height(8.dp))
          FooterLink(text = "Home", onClick = { onNavigateTab(ActiveTab.HOME) })
          FooterLink(text = "Shop", onClick = { onNavigateTab(ActiveTab.SHOP) })
          FooterLink(text = "Categories", onClick = { onNavigateTab(ActiveTab.CATEGORIES) })
          FooterLink(text = "About Us", onClick = { onNavigateTab(ActiveTab.ABOUT) })
          FooterLink(text = "Contact", onClick = { onNavigateTab(ActiveTab.CONTACT) })
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Customer Support
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Customer Care",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = MutedYellow
          )
          Spacer(modifier = Modifier.height(8.dp))
          FooterLink(text = "FAQ", onClick = { onOpenSupportInfo("Frequently Asked Questions") })
          FooterLink(text = "Shipping Info", onClick = { onOpenSupportInfo("Shipping & Delivery Policy") })
          FooterLink(text = "Returns & Refund", onClick = { onOpenSupportInfo("Returns & Refund Policy") })
          FooterLink(text = "Privacy Policy", onClick = { onOpenSupportInfo("Privacy & Data Policy") })
          FooterLink(text = "Terms of Service", onClick = { onOpenSupportInfo("Terms & Conditions") })
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Social Placeholders
      Text(
        text = "Follow Us Online",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = MutedYellow
      )
      Spacer(modifier = Modifier.height(8.dp))

      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        SocialTag(name = "Facebook", onClick = { onOpenSupportInfo("Follow us on Facebook (Placeholder)") })
        SocialTag(name = "Instagram", onClick = { onOpenSupportInfo("Follow us on Instagram (Placeholder)") })
        SocialTag(name = "YouTube", onClick = { onOpenSupportInfo("Subscribe on YouTube (Placeholder)") })
      }

      Spacer(modifier = Modifier.height(24.dp))
      HorizontalDivider(color = ForestGreen.copy(alpha = 0.5f))
      Spacer(modifier = Modifier.height(14.dp))

      // Copyright & Brand rule
      Text(
        text = "© 2026 Digital Online Shop BD. All rights reserved.",
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
        color = WarmOffWhite.copy(alpha = 0.65f)
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = "PURE • NATURAL • TRUSTED • MODERN",
        style = MaterialTheme.typography.labelSmall.copy(
          letterSpacing = 1.sp,
          fontWeight = FontWeight.Bold
        ),
        color = LeafGreen
      )
    }
  }
}

@Composable
private fun FooterLink(
  text: String,
  onClick: () -> Unit
) {
  Text(
    text = text,
    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
    color = WarmOffWhite.copy(alpha = 0.85f),
    modifier = Modifier
      .clickable(onClick = onClick)
      .padding(vertical = 3.dp)
  )
}

@Composable
private fun SocialTag(
  name: String,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(12.dp))
      .background(ForestGreen.copy(alpha = 0.6f))
      .clickable(onClick = onClick)
      .padding(horizontal = 10.dp, vertical = 5.dp)
  ) {
    Text(
      text = name,
      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
      color = SoftCream
    )
  }
}
