package dev.tekofx.teatime.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.kdroid.composenotification.builder.ExperimentalNotificationsApi
import dev.tekofx.teatime.AppViewModel
import dev.tekofx.teatime.model.Tea
import org.jetbrains.compose.resources.ExperimentalResourceApi


@OptIn(ExperimentalNotificationsApi::class, ExperimentalResourceApi::class)
@Composable
fun TeaButton(
    tea: Tea,
    viewModel: AppViewModel,
    notificationMessage: String?,
    onShowMessage: (String?) -> Unit
) {

    val activeTea = viewModel.activeTea.collectAsState()

    Button(
        onClick = {
            viewModel.setActiveTea(tea)

        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (activeTea.value == tea) MaterialTheme.colors.surface else MaterialTheme.colors.primarySurface
        )
    ) {
        Column {
            Text(tea.name)
            Text("${tea.temperature} ªC")
            Text(tea.formattedTime)
            Text(tea.quantity)
        }
    }
}