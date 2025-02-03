package dev.tekofx.teatime

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val CustomPrimary = Color(0xFFD0BCFF)
val CustomPrimaryVariant = Color(0xFFCCC2DC)
val CustomSecondary = Color(0xFFEFB8C8)



@Composable
fun CustomMaterialTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            primary = CustomPrimary,
            primaryVariant = CustomPrimaryVariant,
            secondary = CustomSecondary
        ),
        content = content
    )
}