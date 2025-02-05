package dev.tekofx.teatime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kdroid.composenotification.builder.getNotificationProvider
import dev.tekofx.teatime.components.MainScaffold
import dev.tekofx.teatime.components.TeaButton
import dev.tekofx.teatime.components.TimerLabel
import dev.tekofx.teatime.navigation.navigationItemsLists
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun App(viewModel: AppViewModel) {
    val notificationProvider = getNotificationProvider()
    val teas = viewModel.teas.collectAsState()
    val time=viewModel.formattedRemainingTime
    var notificationMessage by remember { mutableStateOf<String?>(null) }
    var permissionDenied by remember { mutableStateOf(false) }

    CustomMaterialTheme {
        MainScreen()

//        Column(
//            Modifier.fillMaxWidth()
//                .padding(vertical = 20.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(20.dp)
//        ) {
//            TimerLabel(time)
//            if (permissionDenied) {
//                Text("Permission denied. Please enable notifications in settings.", color = Color.Red)
//            }
//            Button(
//                onClick = {
//                    notificationProvider.requestPermission(
//                        onGranted = {
//                            notificationProvider.updatePermissionState(true)
//                        },
//                        onDenied = {
//                            notificationProvider.updatePermissionState(false)
//                            permissionDenied = true
//                        }
//                    )
//                }
//            ) {
//                Text("Grant permission to show notifications")
//            }
//
//            FlowRow {
//                teas.value.forEach { tea ->
//                    TeaButton(tea, viewModel,
//                        notificationMessage = notificationMessage,
//                        onShowMessage = { message -> notificationMessage = message }
//                    )
//                }
//            }
//        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen() {
    val windowSizeClass = calculateWindowSizeClass()
    val isMediumExpandedWWSC by remember(windowSizeClass) {
        derivedStateOf {
            windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
        }
    }
    val rootNavController = rememberNavController()
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
    val currentRoute by remember(navBackStackEntry) {
        derivedStateOf {
            navBackStackEntry?.destination?.route
        }
    }
    val navigationItem by remember {
        derivedStateOf {
            navigationItemsLists.find { it.route == currentRoute }
        }
    }
    val isMainScreenVisible by remember(isMediumExpandedWWSC) {
        derivedStateOf {
            navigationItem != null
        }
    }
    val isBottomBarVisible by remember(isMediumExpandedWWSC) {
        derivedStateOf {
            if (!isMediumExpandedWWSC) {
                navigationItem != null
            } else {
                false
            }
        }
    }
    MainScaffold(
        rootNavController = rootNavController,
        currentRoute = currentRoute,
        isMediumExpandedWWSC = isMediumExpandedWWSC,
        isBottomBarVisible = isBottomBarVisible,
        isMainScreenVisible = isMainScreenVisible,
        onItemClick = { currentNavigationItem ->
            rootNavController.navigate(currentNavigationItem.route) {
                popUpTo(rootNavController.graph.startDestinationRoute ?: "") {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}