package dev.tekofx.teatime

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kdroid.composenotification.builder.AppConfig
import com.kdroid.composenotification.builder.NotificationInitializer
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import teatime.composeapp.generated.resources.Res
import teatime.composeapp.generated.resources.tea
import teatime.composeapp.generated.resources.timer_filled

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
     val viewModel = AppViewModel()
    val icon= painterResource(Res.drawable.timer_filled)

    NotificationInitializer.configure(
        AppConfig(
            appName = "Tea Time",
            smallIcon = Res.getUri("drawable/tea.png"),
        )
    )

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