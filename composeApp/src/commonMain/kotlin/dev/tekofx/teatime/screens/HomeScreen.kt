package dev.tekofx.teatime.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import com.kdroid.composenotification.builder.getNotificationProvider
import dev.tekofx.teatime.AppViewModel
import dev.tekofx.teatime.components.TeaButton
import dev.tekofx.teatime.components.TimerLabel
import dev.tekofx.teatime.navigation.Routes

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    rootNavController: NavController,
    paddingValues: PaddingValues,
    viewModel: AppViewModel
) {

    // Data
    val teas by viewModel.teas.collectAsState()
    val timer by viewModel.timer.collectAsState()

    // Input
    val activeTea by viewModel.activeTea.collectAsState()

    // Notifications
    val notificationProvider = getNotificationProvider()
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
                    text = Routes.Home.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
            }
        )
        TimerLabel(timer)

        if (!notificationProvider.hasPermission()){
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


        FlowRow {
            teas.forEach { tea ->
                TeaButton(
                    tea = tea,
                    activeTea = activeTea,
                    setActiveTea = {viewModel.setActiveTea(tea)},
                    notificationMessage = notificationMessage,
                    onShowMessage = { message -> notificationMessage = message }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

}