package dev.tekofx.teatime

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import teatime.composeapp.generated.resources.Res
import teatime.composeapp.generated.resources.tea
import teatime.composeapp.generated.resources.timer_filled

fun main() = application {
    val desktopTimerManager=DesktopTimerManager()
     val viewModel = AppViewModel(desktopTimerManager)
    val icon= painterResource(Res.drawable.timer_filled)

    Window(
        onCloseRequest = ::exitApplication,
        title = "TeaTime",
        icon= icon
    ) {
        App(
            darkTheme = isSystemInDarkTheme(),
            dynamicColor = false,
            viewModel
        )
    }
}