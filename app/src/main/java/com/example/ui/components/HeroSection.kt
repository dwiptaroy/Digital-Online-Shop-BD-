package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.MutedYellow
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite

@Composable
fun HeroSection(
  onShopNowClick: () -> Unit,
  onExploreProductsClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    // Editorial Signature Hero Banner
    Card(
      shape = RoundedCornerShape(36.dp),
      colors = CardDefaults.cardColors(containerColor = LeafGreen),
      elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.linearGradient(
              colors = listOf(LeafGreen, Color(0xFF5E8044), ForestGreenDark)
            )
          )
      ) {
        // Decorative glowing circle in corner
        Box(
          modifier = Modifier
            .size(160.dp)
            .align(Alignment.TopEnd)
            .clip(CircleShape)
            .background(
              Brush.radialGradient(
                colors = listOf(MutedYellow.copy(alpha = 0.35f), Color.Transparent)
              )
            )
        )

        // Watermark Eco Icon in background
        Icon(
          imageVector = Icons.Default.Spa,
          contentDescription = null,
          tint = Color.White.copy(alpha = 0.15f),
          modifier = Modifier
            .size(110.dp)
            .align(Alignment.TopEnd)
            .padding(top = 12.dp, end = 16.dp)
        )

        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp)
        ) {
          // Pill tag "ORGANIC PROMISE"
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clip(RoundedCornerShape(30.dp))
              .background(Color.White.copy(alpha = 0.22f))
              .padding(horizontal = 12.dp, vertical = 5.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Eco,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "ORGANIC PROMISE",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                letterSpacing = 1.2.sp
              ),
              color = Color.White
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Headline
          Text(
            text = "Pure Food.\nNaturally Better.",
            style = MaterialTheme.typography.displayMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 30.sp,
              lineHeight = 36.sp,
              letterSpacing = (-0.5).sp
            ),
            color = Color.White
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "Wholesome, raw and chemical-free products carefully sourced for your home in Bangladesh.",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontSize = 13.sp,
              lineHeight = 19.sp
            ),
            color = Color.White.copy(alpha = 0.9f)
          )

          Spacer(modifier = Modifier.height(18.dp))

          // Action Buttons
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Button(
              onClick = onShopNowClick,
              shape = RoundedCornerShape(30.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = ForestGreen
              ),
              elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
              modifier = Modifier
                .height(44.dp)
                .weight(1f)
                .testTag("hero_shop_now_button")
            ) {
              Text(
                text = "Shop Now",
                style = MaterialTheme.typography.labelLarge.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 14.sp
                ),
                color = ForestGreen
              )
            }

            OutlinedButton(
              onClick = onExploreProductsClick,
              shape = RoundedCornerShape(30.dp),
              colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
              ),
              border = androidx.compose.foundation.BorderStroke(1.2.dp, Color.White.copy(alpha = 0.8f)),
              modifier = Modifier
                .height(44.dp)
                .weight(1f)
                .testTag("hero_explore_products_button")
            ) {
              Text(
                text = "Explore",
                style = MaterialTheme.typography.labelLarge.copy(
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 14.sp
                ),
                color = Color.White
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Hero Organic Image Showcase with rounded container
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(22.dp))
              .background(WarmOffWhite)
          ) {
            Image(
              painter = painterResource(id = R.drawable.img_hero_organic),
              contentDescription = "Fresh organic fruits, vegetables, and honey",
              modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 8.5f),
              contentScale = ContentScale.Crop
            )

            // Organic Badge overlay
            Box(
              modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(ForestGreenDark.copy(alpha = 0.9f))
                .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text(
                text = "🌿 100% Wholesome Selection",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = Color.White
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Trust Indicators Row in Crisp White Editorial Cards
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      TrustBadge(
        icon = Icons.Default.Spa,
        title = "100% Natural",
        subtitle = "Selection",
        modifier = Modifier.weight(1f)
      )
      Spacer(modifier = Modifier.width(8.dp))
      TrustBadge(
        icon = Icons.Default.CheckCircle,
        title = "Quality",
        subtitle = "Checked",
        modifier = Modifier.weight(1f)
      )
      Spacer(modifier = Modifier.width(8.dp))
      TrustBadge(
        icon = Icons.Default.LocalFlorist,
        title = "Freshly",
        subtitle = "Sourced",
        modifier = Modifier.weight(1f)
      )
    }
  }
}

@Composable
private fun TrustBadge(
  icon: ImageVector,
  title: String,
  subtitle: String,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(20.dp),
    color = SurfaceCard,
    border = androidx.compose.foundation.BorderStroke(1.dp, SubtleBorder),
    shadowElevation = 0.5.dp,
    modifier = modifier
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(SoftSage),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = ForestGreen,
          modifier = Modifier.size(16.dp)
        )
      }
      Spacer(modifier = Modifier.width(6.dp))
      Column {
        Text(
          text = title,
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
          color = DeepTextGreen,
          fontSize = 11.sp
        )
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp),
          color = DeepTextGreen.copy(alpha = 0.7f)
        )
      }
    }
  }
}
