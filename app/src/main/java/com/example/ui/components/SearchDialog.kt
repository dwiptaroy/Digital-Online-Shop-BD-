package com.example.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.data.model.Product
import com.example.ui.theme.DeepTextGreen
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LeafGreen
import com.example.ui.theme.SoftCream
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.WarmOffWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDialog(
  searchQuery: String,
  products: List<Product>,
  onSearchChange: (String) -> Unit,
  onProductClick: (Product) -> Unit,
  onClose: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onClose,
    sheetState = sheetState,
    containerColor = SoftCream,
    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(bottom = 28.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "Search Organic Store",
          style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
          color = DeepTextGreen
        )
        IconButton(onClick = onClose) {
          Icon(Icons.Default.Close, contentDescription = "Close search", tint = DeepTextGreen)
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchChange,
        placeholder = { Text("Type mango, honey, oil, chia seeds, spices...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ForestGreen) },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { onSearchChange("") }) {
              Icon(Icons.Default.Clear, contentDescription = "Clear", tint = ForestGreen)
            }
          }
        },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = ForestGreen,
          unfocusedBorderColor = SubtleBorder,
          focusedContainerColor = WarmOffWhite,
          unfocusedContainerColor = WarmOffWhite
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth().testTag("modal_search_input")
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Quick Search Suggestions
      Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text("Popular:", style = MaterialTheme.typography.labelSmall, color = LeafGreen)
        listOf("Honey", "Mango", "Mustard Oil", "Chia", "Dates").forEach { term ->
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(WarmOffWhite)
              .clickable { onSearchChange(term) }
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = term,
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
              color = ForestGreen
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Results List
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .height(280.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(products, key = { it.id }) { product ->
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = WarmOffWhite),
            modifier = Modifier
              .fillMaxWidth()
              .clickable {
                onProductClick(product)
                onClose()
              }
          ) {
            Row(
              modifier = Modifier.padding(10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(46.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(SoftCream)
              ) {
                Image(
                  painter = painterResource(id = product.imageRes),
                  contentDescription = product.name,
                  modifier = Modifier.fillMaxWidth(),
                  contentScale = ContentScale.Crop
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = product.name,
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                  color = DeepTextGreen
                )
                Text(
                  text = "${product.category} • ${product.unit}",
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                  color = DeepTextGreen.copy(alpha = 0.7f)
                )
              }
              Text(
                text = "৳${product.price}",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = ForestGreen
              )
            }
          }
        }
      }
    }
  }
}
