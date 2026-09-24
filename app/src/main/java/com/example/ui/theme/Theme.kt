package com.example.ui.theme

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
    primary = MedicalGreenLight,
    onPrimary = Color.White,
    primaryContainer = MedicalGreenDark,
    onPrimaryContainer = MedicalGreenContainer,
    secondary = HealthcareBlueLight,
    onSecondary = Color.Black,
    secondaryContainer = HealthcareBlueDark,
    onSecondaryContainer = HealthcareBlueContainer,
    tertiary = TertiaryTeal,
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    surfaceVariant = Color(0xFF334155),
    error = EmergencyRed,
    errorContainer = EmergencyRedDark,
    onError = Color.White,
    onErrorContainer = EmergencyRedContainer
)

private val LightColorScheme = lightColorScheme(
    primary = MedicalGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = MedicalGreenContainer,
    onPrimaryContainer = OnMedicalGreenContainer,
    secondary = HealthcareBluePrimary,
    onSecondary = Color.White,
    secondaryContainer = HealthcareBlueContainer,
    onSecondaryContainer = OnHealthcareBlueContainer,
    tertiary = TertiaryTeal,
    onTertiary = Color.White,
    tertiaryContainer = TertiaryTealContainer,
    background = MedicalBackground,
    onBackground = MedicalTextPrimary,
    surface = MedicalSurface,
    onSurface = MedicalTextPrimary,
    surfaceVariant = MedicalSurfaceVariant,
    onSurfaceVariant = MedicalTextSecondary,
    outline = MedicalOutline,
    error = EmergencyRed,
    errorContainer = EmergencyRedContainer,
    onError = Color.White,
    onErrorContainer = OnEmergencyRedContainer
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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
