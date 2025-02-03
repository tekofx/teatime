package dev.tekofx.teatime

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.kdroid.composenotification.builder.ExperimentalNotificationsApi
import com.kdroid.composenotification.builder.Notification
import org.jetbrains.compose.resources.ExperimentalResourceApi
import teatime.composeapp.generated.resources.Res


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
            backgroundColor = if (activeTea.value == tea) Color.Green else Color.Red
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