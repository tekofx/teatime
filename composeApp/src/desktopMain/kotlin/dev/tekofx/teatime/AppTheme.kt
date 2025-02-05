package dev.tekofx.teatime

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.tekofx.teatime.theme.DarkColorScheme
import dev.tekofx.teatime.theme.LightColorScheme
import dev.tekofx.teatime.theme.Typography

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}