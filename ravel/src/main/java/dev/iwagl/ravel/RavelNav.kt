package dev.iwagl.ravel

import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.*
import androidx.navigation.compose.*
import dev.iwagl.ravel.ui.adventuredetails.*

internal sealed class Screen(val route: String) {
    object Discover : Screen("discover")
    object Workshop : Screen("workshop")
    object Profile : Screen("profile")
    object Search : Screen("search")
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Discover : LeafScreen("discover")
    object Profile : LeafScreen("profile")
    object Workshop : LeafScreen("workshop")
    object Search : LeafScreen("search")

    object AdventureDetails : LeafScreen("adventure/{adventureId}") {
        fun createRoute(root: Screen, adventureId: Long): String {
            return "${root.route}/adventure/$adventureId"
        }
    }
}

@ExperimentalAnimationApi
@Composable
internal fun RavelNav(
    navController: NavHostController,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Discover.route,
//        enterTransition = { initial, target -> defaultRavelEnterTransition(initial, target) },
//        exitTransition = { initial, target -> defaultRavelExitTransition(initial, target) },
//        popEnterTransition = { _, _ -> defaultRavelPopEnterTransition() },
//        popExitTransition = { _, _ -> defaultRavelPopExitTransition() },
        modifier = modifier,
    ) {
        addDiscoverTopLevel(navController, onOpenSettings)
        addProfileTopLevel(navController, onOpenSettings)
        addWorkshopTopLevel(navController, onOpenSettings)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addDiscoverTopLevel(
    navController: NavController,
    openSettings: () -> Unit,
) {
    navigation(
        route = Screen.Discover.route,
        startDestination = LeafScreen.Discover.createRoute(Screen.Discover),
    ) {
        addDiscover(navController, Screen.Discover)
        addAdventureDetails(navController, Screen.Discover)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addProfileTopLevel(
    navController: NavController,
    openSettings: () -> Unit,
) {
    navigation(
        route = Screen.Profile.route,
        startDestination = LeafScreen.Profile.createRoute(Screen.Profile),
    ) {
        addProfile(navController, Screen.Profile)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkshopTopLevel(
    navController: NavController,
    openSettings: () -> Unit,
) {
    navigation(
        route = Screen.Workshop.route,
        startDestination = LeafScreen.Workshop.createRoute(Screen.Workshop),
    ) {
        addWorkshop(navController, Screen.Workshop)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addDiscover(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Discover.createRoute(root)) {
        Discover(
            openAdventure = { adventureId ->
                navController.navigate(LeafScreen.AdventureDetails.createRoute(root, adventureId = adventureId))
            }
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addAdventureDetails(
    navController: NavController,
    root: Screen,
) {
    composable(
        route = LeafScreen.AdventureDetails.createRoute(root),
        arguments = listOf(
            navArgument("adventureId") { type = NavType.LongType }
        )
    ) {
        AdventureDetails(
            navigateUp = navController::navigateUp
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addProfile(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Profile.createRoute(root)) {
        Text(
            text = "HOW ABOUT NOW?",
            modifier = Modifier.statusBarsPadding()
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkshop(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Workshop.createRoute(root)) {
        Workshop(
            openPreview = {
                navController.navigate(LeafScreen.Profile.createRoute(root))
            },
        )
    }
}

private val NavDestination.hostNavGraph: NavGraph
    get() = hierarchy.first { it is NavGraph }

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultRavelEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): EnterTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeIn()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Start)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultRavelExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): ExitTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeOut()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Start)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultRavelPopEnterTransition(): EnterTransition {
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.End)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultRavelPopExitTransition(): ExitTransition {
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.End)
}
