package com.ntc.shopree.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ColorPrimary200, // Light gray for primary in dark mode
    onPrimary = Neutral950,
    secondary = ColorSecondary400, // Medium gray for secondary
    onSecondary = Neutral950,
    tertiary = ColorSecondary300,
    onTertiary = Neutral950,
    background = Neutral950, // Pure dark background
    onBackground = Neutral100,
    surface = Neutral900,
    onSurface = Neutral100,
    surfaceVariant = Neutral800,
    onSurfaceVariant = Neutral200,
    error = Neutral400,
    onError = Neutral950,
    surfaceContainerHigh = ColorGrey100
)

private val LightColorScheme = lightColorScheme(
    primary = Neutral900, // Dark gray/black for primary in light mode
    onPrimary = Neutral50,
    secondary = Neutral600, // Medium gray for secondary
    onSecondary = Color.White,
    tertiary = Neutral400,
    onTertiary = Color.White,
    background = Neutral50, // Very light gray background
    onBackground = Neutral900,
    surface = Color.White,
    onSurface = Neutral900,
    surfaceVariant = Neutral100,
    onSurfaceVariant = Neutral600,
    error = Neutral600,
    onError = Color.White,
    surfaceContainerHigh = ColorGrey100
)

@Composable
fun MobileAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
