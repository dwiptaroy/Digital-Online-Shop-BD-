package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
  primary = ForestGreen,
  onPrimary = SoftCream,
  primaryContainer = SoftSage,
  onPrimaryContainer = ForestGreenDark,
  secondary = LeafGreen,
  onSecondary = SoftCream,
  secondaryContainer = SageTint,
  onSecondaryContainer = DeepTextGreen,
  tertiary = EarthyBrown,
  onTertiary = SoftCream,
  tertiaryContainer = WarmOffWhite,
  onTertiaryContainer = DeepTextGreen,
  background = WarmOffWhite,
  onBackground = DeepTextGreen,
  surface = SurfaceCard,
  onSurface = DeepTextGreen,
  surfaceVariant = WarmOffWhite,
  onSurfaceVariant = TextMuted,
  outline = EditorialBorder,
  outlineVariant = SoftSage,
)

private val DarkColorScheme = darkColorScheme(
  primary = LightLeafGreen,
  onPrimary = ForestGreenDark,
  primaryContainer = ForestGreen,
  onPrimaryContainer = SoftCream,
  secondary = LeafGreen,
  onSecondary = ForestGreenDark,
  tertiary = EarthyBrownLight,
  onTertiary = ForestGreenDark,
  background = DeepTextGreen,
  onBackground = SoftCream,
  surface = ForestGreenDark,
  onSurface = SoftCream,
  surfaceVariant = Color(0xFF2C3E31),
  onSurfaceVariant = WarmOffWhite,
  outline = LeafGreen,
  outlineVariant = Color(0xFF384D3E),
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = false, // Keep default warm light organic aesthetic as requested
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
