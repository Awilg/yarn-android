package dev.iwagl.ravel.ui.home

import androidx.annotation.*
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.accompanist.insets.*
import com.google.accompanist.navigation.animation.*
import dev.iwagl.ravel.*
import dev.iwagl.ravel.R

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
internal fun Home() {
    val navController = rememberAnimatedNavController()

    val configuration = LocalConfiguration.current
    val useBottomNavigation by remember {
        derivedStateOf { configuration.smallestScreenWidthDp < 600 }
    }

    Scaffold(
        bottomBar = {
            if (useBottomNavigation) {
                val currentSelectedItem by navController.currentScreenAsState()
                HomeBottomNavigation(
                    selectedNavigation = currentSelectedItem,
                    onNavigationSelected = { selected ->
                        navController.navigate(selected.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    },
                    modifier = Modifier.navigationBarsPadding().fillMaxWidth()
                )
            }
        }
    ) {
        Row(Modifier.fillMaxWidth()) {
            RavelNav(
                navController = navController,
                onOpenSettings = { openSettings() },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            )
        }
    }
}

private fun openSettings() {
}

/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Stable
@Composable
private fun NavController.currentScreenAsState(): State<Screen> {
    val selectedItem = remember { mutableStateOf<Screen>(Screen.Discover) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == Screen.Discover.route } -> {
                    selectedItem.value = Screen.Discover
                }
                destination.hierarchy.any { it.route == Screen.Search.route } -> {
                    selectedItem.value = Screen.Search
                }
                destination.hierarchy.any { it.route == Screen.Profile.route } -> {
                    selectedItem.value = Screen.Profile
                }
                destination.hierarchy.any { it.route == Screen.Workshop.route } -> {
                    selectedItem.value = Screen.Workshop
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}

@Composable
internal fun HomeBottomNavigation(
    selectedNavigation: Screen,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface.copy(),
        contentColor = contentColorFor(MaterialTheme.colors.surface),
        modifier = modifier.imePadding()
    ) {
        HomeNavigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    HomeNavigationItemIcon(
                        item = item,
                        selected = selectedNavigation == item.screen
                    )
                },
                label = { Text(text = stringResource(item.labelResId)) },
                selected = selectedNavigation == item.screen,
                onClick = { onNavigationSelected(item.screen) },
            )
        }
    }
}

@Composable
private fun HomeNavigationItemIcon(item: HomeNavigationItem, selected: Boolean) {
    val painter = when (item) {
        is HomeNavigationItem.ResourceIcon -> painterResource(item.iconResId)
        is HomeNavigationItem.ImageVectorIcon -> rememberVectorPainter(item.iconImageVector)
    }
    val selectedPainter = when (item) {
        is HomeNavigationItem.ResourceIcon -> item.selectedIconResId?.let { painterResource(it) }
        is HomeNavigationItem.ImageVectorIcon -> item.selectedImageVector?.let { rememberVectorPainter(it) }
    }

    if (selectedPainter != null) {
        Crossfade(targetState = selected) {
            Icon(
                painter = if (it) selectedPainter else painter,
                contentDescription = stringResource(item.contentDescriptionResId),
                modifier = Modifier.size(24.dp),
            )
        }
    } else {
        Icon(
            painter = painter,
            contentDescription = stringResource(item.contentDescriptionResId),
            modifier = Modifier.size(24.dp),
        )
    }
}

private sealed class HomeNavigationItem(
    val screen: Screen,
    @StringRes val labelResId: Int,
    @StringRes val contentDescriptionResId: Int,
) {
    class ResourceIcon(
        screen: Screen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        @DrawableRes val iconResId: Int,
        @DrawableRes val selectedIconResId: Int? = null,
    ) : HomeNavigationItem(screen, labelResId, contentDescriptionResId)

    class ImageVectorIcon(
        screen: Screen,
        @StringRes labelResId: Int,
        @StringRes contentDescriptionResId: Int,
        val iconImageVector: ImageVector,
        val selectedImageVector: ImageVector? = null,
    ) : HomeNavigationItem(screen, labelResId, contentDescriptionResId)
}

private val HomeNavigationItems = listOf(
    HomeNavigationItem.ResourceIcon(
        screen = Screen.Discover,
        labelResId = R.string.discover,
        contentDescriptionResId = R.string.discover,
        iconResId = R.drawable.ic_icons8_compass_50_2,
        selectedIconResId = R.drawable.ic_icons8_compass_50_2,
    ),
//    HomeNavigationItem.ImageVectorIcon(
//        screen = Screen.Create,
//        labelResId = R.string.following_shows_title,
//        contentDescriptionResId = R.string.cd_following_shows_title,
//        iconImageVector = Icons.Default.FavoriteBorder,
//        selectedImageVector = Icons.Default.Favorite,
//    ),
    HomeNavigationItem.ResourceIcon(
        screen = Screen.Profile,
        labelResId = R.string.profile,
        contentDescriptionResId = R.string.profile,
        iconResId = R.drawable.ic_icons8_user_50_2,
        selectedIconResId = R.drawable.ic_icons8_user_50_2,
    ),
    HomeNavigationItem.ResourceIcon(
        screen = Screen.Workshop,
        labelResId = R.string.create,
        contentDescriptionResId = R.string.create,
        iconResId = R.drawable.ic_icons8_creating_50_2,
        selectedIconResId = R.drawable.ic_icons8_creating_50_2,
    ),
    HomeNavigationItem.ResourceIcon(
        screen = Screen.Profile,
        labelResId = R.string.search,
        contentDescriptionResId = R.string.search,
        iconResId = R.drawable.ic_search_black_24dp,
        selectedIconResId = R.drawable.ic_search_black_24dp,
    )
)
