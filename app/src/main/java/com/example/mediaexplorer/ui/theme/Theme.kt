package com.example.mediaexplorer.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFA31D1D),     // Rojo oscuro
    secondary = Color(0xFFD84040),   // Rojo claro
    tertiary = Color(0xFFB39D7F),    // Beige más apagado
    background = Color(0xFF282727),  // Negro/gris oscuro
    surface = Color(0xFF1E1E1E),     // Superficie oscura

    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFD84040),     // Rojo claro (Botones, FAB, etc.)
    secondary = Color(0xFFA31D1D),   // Rojo oscuro
    tertiary = Color(0xFFECDCBF),    // Beige claro (Tarjetas, AppBars)
    background = Color(0xFFF8F2DE),  // Fondo general de la app
    surface = Color(0xFFECDCBF),     // Fondo de AppBar, Cards, etc.

    onPrimary = Color.Black,         // Texto sobre primary
    onSecondary = Color.White,       // Texto sobre secondary
    onTertiary = Color.Black,        // Texto sobre tertiary
    onBackground = Color.Black,      // Texto sobre fondo
    onSurface = Color.Black          // Texto sobre surface
)

@Composable
fun MediaExplorerTheme(
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