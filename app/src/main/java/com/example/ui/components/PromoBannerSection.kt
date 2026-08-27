package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.ForestGreenDark
import com.example.ui.theme.HarvestGold
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.MutedYellow
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage

@Composable
fun PromoBannerSection(
  onExploreCollection: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = MutedYellow.copy(alpha = 0.22f)),
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
    ) {
      // Promo image container
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(16f / 8.5f)
          .clip(RoundedCornerShape(18.dp))
          .background(SoftCream)
      ) {
        Image(
          painter = painterResource(id = R.drawable.img_promo_harvest),
          contentDescription = "Spices, dry fruits and harvest",
          modifier = Modifier.fillMaxWidth(),
          contentScale = ContentScale.Crop
        )

        Box(
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ForestGreen.copy(alpha = 0.9f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "Seasonal Harvest Collection",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = SoftCream
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "Bring Nature To Your Everyday Table",
        style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.Bold,
          lineHeight = 28.sp
        ),
        color = DeepTextGreen
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Explore our collection of natural food products and discover simple choices for everyday living.",
        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 21.sp),
        color = DeepTextGreen.copy(alpha = 0.85f)
      )

      Spacer(modifier = Modifier.height(14.dp))

      Button(
        onClick = onExploreCollection,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = ForestGreen,
          contentColor = SoftCream
        ),
        modifier = Modifier
          .height(46.dp)
          .testTag("explore_collection_button")
      ) {
        Icon(
          imageVector = Icons.Default.Eco,
          contentDescription = null,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Explore Collection",
          style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
        )
      }
    }
  }
}
