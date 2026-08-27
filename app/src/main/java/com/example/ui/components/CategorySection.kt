package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ProductCategory
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.EarthyBrown
import com.example.ui.theme.EditorialBorder
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.MutedYellow
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SoftSage
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.WarmOffWhite

@Composable
fun CategorySection(
  categories: List<ProductCategory>,
  selectedCategory: String,
  onCategoryClick: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    // Section Header with View All
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column {
        Text(
          text = "Categories",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
          ),
          color = DeepTextGreen
        )
      }

      Text(
        text = "View All",
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 12.sp
        ),
        color = LeafGreen,
        modifier = Modifier
          .clickable { onCategoryClick("All") }
          .padding(4.dp)
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Editorial Horizontal Quick Icon Capsules
    val quickCapsules = listOf(
      QuickCategoryCapsule("Natural Honey", "Honey", "🍯", MutedYellow.copy(alpha = 0.25f)),
      QuickCategoryCapsule("Organic Vegetables", "Veggies", "🥗", LeafGreen.copy(alpha = 0.22f)),
      QuickCategoryCapsule("Dry Fruits", "Nuts", "🥜", EarthyBrown.copy(alpha = 0.22f)),
      QuickCategoryCapsule("Fresh Fruits", "Fruits", "🍎", ForestGreen.copy(alpha = 0.2f)),
      QuickCategoryCapsule("Organic Spices", "Spices", "🌿", LeafGreen.copy(alpha = 0.25f)),
      QuickCategoryCapsule("Healthy Snacks", "Snacks", "🌾", MutedYellow.copy(alpha = 0.25f))
    )

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
      horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      quickCapsules.forEach { capsule ->
        val isSelected = selectedCategory.equals(capsule.categoryName, ignoreCase = true)
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .clickable { onCategoryClick(capsule.categoryName) }
            .testTag("quick_category_${capsule.label}")
        ) {
          Surface(
            shape = RoundedCornerShape(24.dp),
            color = capsule.bgColor,
            border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, ForestGreen) else null,
            shadowElevation = 0.5.dp,
            modifier = Modifier.size(64.dp)
          ) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = capsule.emoji,
                fontSize = 26.sp
              )
            }
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = capsule.label,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              fontSize = 11.sp
            ),
            color = if (isSelected) ForestGreen else DeepTextGreen
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Full Curated Category Cards in 2-column Grid
    Column(
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      categories.chunked(2).forEach { pair ->
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          pair.forEach { category ->
            CategoryCard(
              category = category,
              isSelected = selectedCategory.equals(category.name, ignoreCase = true),
              onClick = { onCategoryClick(category.name) },
              modifier = Modifier.weight(1f)
            )
          }
          if (pair.size == 1) {
            Spacer(modifier = Modifier.weight(1f))
          }
        }
      }
    }
  }
}

private data class QuickCategoryCapsule(
  val categoryName: String,
  val label: String,
  val emoji: String,
  val bgColor: Color
)

@Composable
fun CategoryCard(
  category: ProductCategory,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(28.dp),
    colors = CardDefaults.cardColors(
      containerColor = SurfaceCard
    ),
    border = if (isSelected) {
      androidx.compose.foundation.BorderStroke(1.5.dp, ForestGreen)
    } else {
      androidx.compose.foundation.BorderStroke(1.dp, EditorialBorder)
    },
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = modifier
      .clickable(onClick = onClick)
      .testTag("category_card_${category.id}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      // Rounded Organic Image Container
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(4f / 3f)
          .clip(RoundedCornerShape(18.dp))
          .background(WarmOffWhite)
      ) {
        Image(
          painter = painterResource(id = category.imageRes),
          contentDescription = category.name,
          modifier = Modifier.fillMaxWidth(),
          contentScale = ContentScale.Crop
        )

        // Badge showing item count
        Box(
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ForestGreen.copy(alpha = 0.88f))
            .padding(horizontal = 7.dp, vertical = 3.dp)
        ) {
          Text(
            text = "${category.itemCount}+ Items",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 9.sp,
              fontWeight = FontWeight.SemiBold
            ),
            color = Color.White
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Category title & arrow
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = category.name,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
          ),
          color = DeepTextGreen,
          modifier = Modifier.weight(1f)
        )

        Box(
          modifier = Modifier
            .size(26.dp)
            .clip(CircleShape)
            .background(if (isSelected) ForestGreen else SoftSage),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "View Category",
            tint = if (isSelected) SoftCream else ForestGreen,
            modifier = Modifier.size(13.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(3.dp))

      Text(
        text = category.shortDescription,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 14.sp),
        color = DeepTextGreen.copy(alpha = 0.65f),
        maxLines = 2
      )
    }
  }
}

