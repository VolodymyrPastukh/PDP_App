package pastukh.vova.appcompose.ui.components.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isDarkMode) DarkColorScheme else LightColorScheme

    return MaterialTheme(
        colorScheme = colorScheme,
        content = content,
        shapes = MaterialTheme.shapes.copy(medium = ShapeDefaults.Large)
    )
}

@Composable
fun AppLightTheme(content: @Composable () -> Unit) {
    return MaterialTheme(
        colorScheme = LightColorScheme,
        content = content,
    )
}

@Composable
fun AppDarkTheme(content: @Composable () -> Unit) {
    return MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content,
    )
}