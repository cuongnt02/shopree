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
    primary = ColorPrimary300,
    onPrimary = Color(0xFF0B1412),
    secondary = ColorSecondary300,
    onSecondary = Color(0xFF2B0C05),
    tertiary = ColorSecondary200,
    onTertiary = ColorGrey700,
    background = Color(0xFF101716),
    onBackground = ColorGrey100,
    surface = Color(0xFF111A19),
    onSurface = ColorGrey100,
    surfaceVariant = Color(0xFF1A2624),
    onSurfaceVariant = ColorGrey200,
    error = Color(0xFFE57373),
    onError = Color(0xFF2B0C05)

    /* Other default colors to override
   background = Color(0xFFFFFBFE),
   surface = Color(0xFFFFFBFE),
   onPrimary = Color.White,
   onSecondary = Color.White,
   onTertiary = Color.White,
   onBackground = Color(0xFF1C1B1F),
   onSurface = Color(0xFF1C1B1F),
   */
)

private val LightColorScheme = lightColorScheme(
    primary = ColorPrimary400,
    onPrimary = Color.White,
    secondary = ColorSecondary300,
    onSecondary = Color.White,
    tertiary = ColorSecondary200,
    onTertiary = ColorGrey700,
    background = Color(0xFFF9FBFA),
    onBackground = ColorGrey700,
    surface = Color.White,
    onSurface = ColorGrey700,
    surfaceVariant = ColorGrey100,
    onSurfaceVariant = ColorGrey600,
    error = Color(0xFFC62828),
    onError = Color.White

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
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
