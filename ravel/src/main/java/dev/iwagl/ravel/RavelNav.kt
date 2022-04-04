package dev.iwagl.ravel

import android.app.*
import android.widget.*
import androidx.compose.animation.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.google.accompanist.insets.*
import com.google.accompanist.permissions.*
import dev.iwagl.ravel.data.models.*
import dev.iwagl.ravel.ui.adventuredetails.*
import dev.iwagl.ravel.ui.common.*
import dev.iwagl.ravel.ui.discover.*
import dev.iwagl.ravel.ui.workshop.*

internal sealed class Screen(val route: String) {
    object Discover : Screen("discover")
    object Workshop : Screen("workshop")
    object Profile : Screen("profile")
    object Search : Screen("search")
}

internal sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Discover : LeafScreen("discover")
    object Profile : LeafScreen("profile")

    // Workshop Routes
    object Workshop : LeafScreen("workshop")
    object WorkflowSummary : LeafScreen("workflowSummary")
    object WorkflowTitleInfo : LeafScreen("workflowTitleInfo")
    object WorkflowPhotos : LeafScreen("workflowPhotos")
    object WorkflowPrivacy : LeafScreen("workflowPrivacy")
    object WorkflowClues : LeafScreen("workflowClues")
    object WorkflowClueSelection : LeafScreen("workflowClueSelection")
    object WorkflowClueTextEditor : LeafScreen("workflowClueTextEditor")
    object WorkflowCluePhotoEditor : LeafScreen("workflowCluePhotoEditor")
    object WorkflowClueLocationEditor : LeafScreen("workflowClueLocationEditor")
    object WorkflowClueLocationSelection : LeafScreen("workflowClueLocationSelection")
    object WorkflowTipsTreasure : LeafScreen("workflowTipsTreasure")
    object WorkflowReview : LeafScreen("workflowReview")
    object WorkflowPreview : LeafScreen("workflowPreview")

    object Search : LeafScreen("search")

    object AdventureDetails : LeafScreen("adventure/{adventureId}") {
        fun createRoute(root: Screen, adventureId: Long): String {
            return "${root.route}/adventure/$adventureId"
        }
    }
}

@ExperimentalPermissionsApi
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

@ExperimentalPermissionsApi
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
        addWorkflowSummary(navController, Screen.Workshop)
        addWorkflowTitleInfo(navController, Screen.Workshop)
        addWorkflowPhotos(navController, Screen.Workshop)
        addWorkflowPrivacy(navController, Screen.Workshop)
        addWorkflowClues(navController, Screen.Workshop)
        addWorkflowClueSelection(navController, Screen.Workshop)
        addWorkflowClueTextEditor(navController, Screen.Workshop)
        addWorkflowCluePhotoEditor(navController, Screen.Workshop)
        addWorkflowClueLocationEditor(navController, Screen.Workshop)
        addWorkflowClueLocationSelection(navController, Screen.Workshop)
        addWorkflowTipsTreasure(navController, Screen.Workshop)
        addWorkflowReview(navController, Screen.Workshop)
        addWorkflowPreview(navController, Screen.Workshop)
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
            createNewAdventure = {
                navController.navigate(LeafScreen.WorkflowSummary.createRoute(root))
            },
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowSummary(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowSummary.createRoute(root)) {
        WorkflowSummary(
            navToTitleInfo= {
                navController.navigate(LeafScreen.WorkflowTitleInfo.createRoute(root))
            },
            navToPhotos= {
                navController.navigate(LeafScreen.WorkflowPhotos.createRoute(root))
            },
            navToPrivacy= {
                navController.navigate(LeafScreen.WorkflowPrivacy.createRoute(root))
            },
            navToClues= {
                navController.navigate(LeafScreen.WorkflowClues.createRoute(root))
            },
            navToTipsTreasure= {
                navController.navigate(LeafScreen.WorkflowTipsTreasure.createRoute(root))
            },
            navToReview= {
                navController.navigate(LeafScreen.WorkflowReview.createRoute(root))
            },
            navToPreview= {
                navController.navigate(LeafScreen.WorkflowPreview.createRoute(root))
            }
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowTitleInfo(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowTitleInfo.createRoute(root)) {
        WorkflowTitleInfo(
            navToNextTask= {
                navController.navigate(LeafScreen.WorkflowPhotos.createRoute(root))
            },
            navToSummary = {
                navController.navigate(LeafScreen.WorkflowSummary.createRoute(root))
            },
        )
    }
}

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowPhotos(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowPhotos.createRoute(root)) {
        WorkflowPhotos(
            navToNextTask= {
                navController.navigate(LeafScreen.WorkflowPrivacy.createRoute(root))
            },
            navToSummary = {
                navController.navigate(LeafScreen.WorkflowSummary.createRoute(root))
            },
        )
    }
}


@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowPrivacy(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowPrivacy.createRoute(root)) {
        WorkflowPrivacy(
            navToNextTask= {
                navController.navigate(LeafScreen.WorkflowClues.createRoute(root))
            },
            navToSummary = {
                navController.navigate(LeafScreen.WorkflowSummary.createRoute(root))
            },
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowClues(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowClues.createRoute(root)) {
        WorkflowClues(
            navToNextTask= {
                navController.navigate(LeafScreen.WorkflowTitleInfo.createRoute(root))
            },
            navToSummary = {
                navController.navigate(LeafScreen.WorkflowSummary.createRoute(root))
            },
            navToClueSelection = {
                navController.navigate(LeafScreen.WorkflowClueSelection.createRoute(root))
            }
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowClueSelection(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowClueSelection.createRoute(root)) {
        WorkflowClueSelection(
            navToClueEditor= { clueType ->
                when(clueType) {
                    ClueType.TEXT -> { navController.navigate(LeafScreen.WorkflowClueTextEditor.createRoute(root)) }
                    ClueType.PHOTO -> { navController.navigate(LeafScreen.WorkflowCluePhotoEditor.createRoute(root)) }
                    ClueType.LOCATION -> { navController.navigate(LeafScreen.WorkflowClueLocationEditor.createRoute(root)) }
                }
            },
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowClueTextEditor(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowClueTextEditor.createRoute(root)) {
        WorkflowClueTextEditor(
            navToClueSummary= {
                navController.navigate(LeafScreen.WorkflowClues.createRoute(root))
            },
        )
    }
}

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowCluePhotoEditor(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowCluePhotoEditor.createRoute(root)) {
        WorkflowCluePhotoEditor(
            navToClueSummary= {
                navController.navigate(LeafScreen.WorkflowClues.createRoute(root))
            }
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowClueLocationEditor(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowClueLocationEditor.createRoute(root)) {
        WorkflowClueLocationEditor(
            navToClueSummary= {
                navController.navigate(LeafScreen.WorkflowClues.createRoute(root))
            },
            navToClueLocationSelection = {
                navController.navigate(LeafScreen.WorkflowClueLocationSelection.createRoute(root))
            }
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowClueLocationSelection(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowClueLocationSelection.createRoute(root)) {
        WorkflowClueLocationSelection(
            navToClueLocationSummary= {
                navController.navigate(LeafScreen.WorkflowClueLocationEditor.createRoute(root))
            },
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowTipsTreasure(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowTipsTreasure.createRoute(root)) {
        WorkflowTipsTreasure(
            navToNextTask= {
                navController.navigate(LeafScreen.WorkflowTitleInfo.createRoute(root))
            },
            navToSummary = {
                navController.navigate(LeafScreen.WorkflowSummary.createRoute(root))
            },
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowReview(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowReview.createRoute(root)) {
        WorkflowReview(
            navToNextTask= {
                navController.navigate(LeafScreen.WorkflowTitleInfo.createRoute(root))
            },
            navToSummary = {
                navController.navigate(LeafScreen.WorkflowSummary.createRoute(root))
            },
        )
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addWorkflowPreview(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.WorkflowPreview.createRoute(root)) {
        WorkflowPreview(
            navToNextTask= {
                navController.navigate(LeafScreen.WorkflowTitleInfo.createRoute(root))
            },
            navToSummary = {
                navController.navigate(LeafScreen.WorkflowSummary.createRoute(root))
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
