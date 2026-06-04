package ni.edu.uam.inventarioacademicopractica.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = AccentTeal,
    surface = SurfaceDark,
    background = BackgroundDark,
    onPrimary = Color.Black,
    onSurface = Color.White,
    error = ErrorRed
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = AccentTeal,
    surface = SurfaceLight,
    background = BackgroundLight,
    onPrimary = Color.White,
    onSurface = TextPrimary,
    onBackground = TextPrimary,
    error = ErrorRed
)

@Composable
fun InventarioAcademicoPracticaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
