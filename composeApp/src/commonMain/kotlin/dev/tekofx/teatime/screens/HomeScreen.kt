package dev.tekofx.teatime.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kdroid.composenotification.builder.getNotificationProvider
import dev.tekofx.teatime.AppViewModel
import dev.tekofx.teatime.components.TeaButton
import dev.tekofx.teatime.components.TimerLabel
import dev.tekofx.teatime.navigation.Routes

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    rootNavController: NavController, paddingValues: PaddingValues,
    viewModel:AppViewModel
) {
    val notificationProvider = getNotificationProvider()
    val teas = viewModel.teas.collectAsState()
    val time=viewModel.formattedRemainingTime
    var notificationMessage by remember { mutableStateOf<String?>(null) }
    var permissionDenied by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        TimerLabel(time)
        if (permissionDenied) {
            androidx.compose.material.Text(
                "Permission denied. Please enable notifications in settings.",
                color = Color.Red
            )
        }
        androidx.compose.material.Button(
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
            androidx.compose.material.Text("Grant permission to show notifications")
        }

        FlowRow {
            teas.value.forEach { tea ->
                TeaButton(tea, viewModel,
                    notificationMessage = notificationMessage,
                    onShowMessage = { message -> notificationMessage = message }
                )
            }
        }

        Text("Home")
    }

}