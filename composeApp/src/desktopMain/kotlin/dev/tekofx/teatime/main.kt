package dev.tekofx.teatime

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
     val viewModel = AppViewModel()
    Window(
        onCloseRequest = ::exitApplication,
        title = "TeaTime",
    ) {
        App(viewModel)
    }
}