package dev.tekofx.teatime.navigation

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.R
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import teatime.composeapp.generated.resources.Res
import teatime.composeapp.generated.resources.inventory_filled
import teatime.composeapp.generated.resources.inventory_outlined
import teatime.composeapp.generated.resources.timer_filled
import teatime.composeapp.generated.resources.timer_outlined

object Graph {
    const val NAVIGATION_BAR_SCREEN_GRAPH = "navigationBarScreenGraph"
}

data class NavigationItem(
    val unSelectedIcon: DrawableResource /* or  DrawableResource*/,
    val selectedIcon: DrawableResource /* or  DrawableResource*/,
    val title: String /* or  StringResource  */,
    val route: String
)
sealed class Routes(var route: String) {
    data object Home : Routes("home")
    data object Inventory : Routes("inventory")
}

val navigationItemsLists = listOf(
    NavigationItem(
        unSelectedIcon = Res.drawable.timer_outlined,
        selectedIcon = Res.drawable.timer_filled,
        title = "Home",
        route = Routes.Home.route,
    ),
    NavigationItem(
        unSelectedIcon = Res.drawable.inventory_outlined,
        selectedIcon = Res.drawable.inventory_filled,
        title = "Inventory",
        route = Routes.Inventory.route,
    ),
)