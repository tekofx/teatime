package dev.tekofx.teatime.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

object Graph {
    const val NAVIGATION_BAR_SCREEN_GRAPH = "navigationBarScreenGraph"
}

data class NavigationItem(
    val unSelectedIcon: ImageVector /* or  DrawableResource*/,
    val selectedIcon: ImageVector /* or  DrawableResource*/,
    val title: String /* or  StringResource  */,
    val route: String
)
sealed class Routes(var route: String) {
    data object Home : Routes("home")
    data object Setting : Routes("setting")
    data object HomeDetail : Routes("homeDetail")
    data object SettingDetail : Routes("settingDetail")
}

val navigationItemsLists = listOf(
    NavigationItem(
        unSelectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        title = "Home",
        route = Routes.Home.route,
    ),
    NavigationItem(
        unSelectedIcon = Icons.Outlined.Search,
        selectedIcon = Icons.Filled.Search,
        title = "Setting",
        route = Routes.Setting.route,
    ),
)