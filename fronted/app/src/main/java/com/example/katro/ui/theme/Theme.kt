package com.example.katro.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Tes couleurs personnalisées
val DarkBrown = Color(0xFF17120D)
val MediumBrown = Color(0xFF423025)
val LightBrown = Color(0xFF685850)
val GreyBrown = Color(0xFF8D8480)
val VeryLightGrey = Color(0xFFBEBBBA)

// Couleurs supplémentaires pour les boutons et accents si besoin
val TealBlue = Color(0xFF4DD0E1) // Boutons ronds de l'image
val PinkishRed = Color(0xFFF06292) // Boutons PLAY/PAUSE de l'image

private val DarkColorScheme = darkColorScheme(
    primary = LightBrown,
    secondary = MediumBrown,
    tertiary = DarkBrown,
    background = DarkBrown,
    surface = MediumBrown,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = LightBrown,
    secondary = MediumBrown,
    tertiary = DarkBrown,
    background = VeryLightGrey,
    surface = LightBrown,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = DarkBrown,
    onSurface = DarkBrown,
)

@Composable
fun KatroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // S'assurer que Typography est défini dans un fichier Typography.kt
        content = content
    )
}