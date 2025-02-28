package dev.tekofx.teatime.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
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
    val formattedTime by viewModel.formattedTime.collectAsState()

    // Input
    val activeTea by viewModel.activeTea.collectAsState()

    // Notifications
    val notificationProvider = getNotificationProvider()
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
        TimerLabel(formattedTime)

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
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(teas){tea->
                TeaButton(
                    tea = tea,
                    activeTea = activeTea,
                    onClick = {viewModel.startTimer(tea)},
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}