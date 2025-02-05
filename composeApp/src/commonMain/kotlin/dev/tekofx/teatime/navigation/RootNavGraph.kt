import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.tekofx.teatime.AppViewModel
import dev.tekofx.teatime.navigation.Graph
import dev.tekofx.teatime.navigation.Routes
import dev.tekofx.teatime.screens.HomeScreen
import dev.tekofx.teatime.screens.InventoryScreen

@Composable
fun RootNavGraph(
    rootNavController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: AppViewModel
) {
    NavHost(
        navController = rootNavController,
        startDestination = Graph.NAVIGATION_BAR_SCREEN_GRAPH,
    ) {
        mainNavGraph(rootNavController = rootNavController, innerPadding = innerPadding,viewModel)
    }
}
fun NavGraphBuilder.mainNavGraph(
    rootNavController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: AppViewModel
) {
    navigation(
        startDestination = Routes.Home.route,
        route = Graph.NAVIGATION_BAR_SCREEN_GRAPH
    ) {
        composable(route = Routes.Home.route) {
            HomeScreen(rootNavController = rootNavController, paddingValues = innerPadding,viewModel)
        }
        composable(route = Routes.Inventory.route) {
            InventoryScreen(rootNavController = rootNavController, paddingValues = innerPadding)
        }
    }
}