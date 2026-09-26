package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DimensioColorScheme = darkColorScheme(
    primary = ElectricCyan,
    onPrimary = OnElectricCyan,
    primaryContainer = ElectricCyanContainer,
    onPrimaryContainer = ElectricCyan,
    secondary = WarmAmber,
    onSecondary = DarkBackground,
    secondaryContainer = WarmAmberContainer,
    onSecondaryContainer = WarmAmberLight,
    tertiary = MintGreen,
    onTertiary = DarkBackground,
    tertiaryContainer = MintGreenContainer,
    onTertiaryContainer = MintGreen,
    background = DarkBackground,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    error = CoralRed,
    onError = DarkBackground,
    errorContainer = CoralRedContainer,
    onErrorContainer = CoralRed
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force high-contrast laboratory dark theme for scientific instrument feel
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = DarkBackground.toArgb()
                window.navigationBarColor = DarkBackground.toArgb()
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = false
                controller.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = DimensioColorScheme,
        typography = Typography,
        content = content
    )
}
