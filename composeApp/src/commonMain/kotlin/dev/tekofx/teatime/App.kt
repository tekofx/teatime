package dev.tekofx.teatime

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kdroid.composenotification.builder.getNotificationProvider
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import teatime.composeapp.generated.resources.Res
import teatime.composeapp.generated.resources.compose_multiplatform

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun App(viewModel: AppViewModel) {
    val notificationProvider = getNotificationProvider()
    val teas = viewModel.teas.collectAsState()
    var notificationMessage by remember { mutableStateOf<String?>(null) }
    var permissionDenied by remember { mutableStateOf(false) }

    CustomMaterialTheme {

        Column(
            Modifier.fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TimerLabel(viewModel.formattedRemainingTime)
            if (permissionDenied) {
                Text("Permission denied. Please enable notifications in settings.", color = Color.Red)
            }
            Button(
                onClick = {
                    notificationProvider.requestPermission(
                        onGranted = {
                            notificationProvider.updatePermissionState(true)
                        },
                        onDenied = {
                            notificationProvider.updatePermissionState(false)
                            permissionDenied = true
                        }
                    )
                }
            ) {
                Text("Grant permission to show notifications")
            }

            FlowRow {
                teas.value.forEach { tea ->
                    TeaButton(tea, viewModel,
                        notificationMessage = notificationMessage,
                        onShowMessage = { message -> notificationMessage = message }
                    )
                }
            }
        }
    }
}