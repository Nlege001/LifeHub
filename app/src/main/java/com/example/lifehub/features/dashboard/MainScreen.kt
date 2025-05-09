package com.example.lifehub.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.analytics.NavFlows
import com.example.core.analytics.Page
import com.example.core.composables.ViewStateCoordinator
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16
import com.example.lifehub.features.dashboard.bottomnav.BottomNavigationBar
import com.example.lifehub.features.dashboard.home.appbar.AppBarController
import com.example.lifehub.features.dashboard.home.appbar.LocalAppBarController
import com.example.lifehub.features.dashboard.sidemenu.SideMenuItem
import com.example.lifehub.features.dashboard.sidemenu.SideMenuViewModel
import com.example.lifehub.features.nav.navbuilder.bottomNavBuilder
import com.example.lifehub.features.nav.navbuilder.sideMenuNavBuilder
import com.example.lifehub.features.profile.ProfilePicture
import com.example.wpinterviewpractice.R
import kotlinx.coroutines.launch
import com.example.core.R as CoreR

@Composable
fun MainScreen(
    startDestination: String = Page.DASHBOARD_HOME.route,
    onSignOut: (String?) -> Unit,
) {
    val appBarController = remember { AppBarController() }
    CompositionLocalProvider(LocalAppBarController provides appBarController) {
        Content(
            startDestination = startDestination,
            appBarController = appBarController,
            onSignOut = onSignOut
        )
    }
}

@Composable
private fun Content(
    mainViewModel: MainViewModel = hiltViewModel(),
    startDestination: String,
    appBarController: AppBarController,
    onSignOut: (String?) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = currentBackStackEntry?.destination?.route
    val currentPage = Page.getCurrentPage(currentRoute)
    val currentPageLabel = currentPage?.label ?: stringResource(CoreR.string.life_hub)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            val viewModel: SideMenuViewModel = hiltViewModel()
            DrawerSheet(
                viewModel = viewModel,
                drawerState = drawerState,
                currentRoute = currentRoute,
                onSideMenuItemSelected = { item ->
                    scope.launch { drawerState.close() }

                    if (item.route == Page.SIGN_OUT.route) {
                        onSignOut(mainViewModel.getUserId())
                    } else {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(NavFlows.BOTTOM_NAV.route) { saveState = true }
                        }
                    }
                },
                onChangeProfilePicture = {
                    scope.launch { drawerState.close() }
                    navController.navigate(Page.PROFILE.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        content = {
            Scaffold(
                topBar = {
                    AppBar(
                        label = currentPageLabel,
                        openSideMenu = { scope.launch { drawerState.open() } },
                        endContent = {
                            appBarController.actions.forEach { action ->
                                IconButton(onClick = action.onClick) {
                                    Icon(
                                        painter = painterResource(action.iconResId),
                                        contentDescription = action.contentDescription,
                                        tint = Color.White
                                    )
                                }
                            }
                        },
                        navBack = { navController.popBackStack() },
                        hasNavDrawer = currentPage?.hasNavDrawer == true
                    )
                },
                bottomBar = {
                    if (currentPage?.hasBottomNav == true) {
                        BottomNavigationBar(navController)
                    }
                },
                content = { padding ->
                    NavHost(
                        modifier = Modifier.padding(padding),
                        navController = navController,
                        startDestination = startDestination,
                        builder = {
                            bottomNavBuilder(
                                navHostController = navController,
                                onSignOut = onSignOut
                            )
                            sideMenuNavBuilder(navController)
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun DrawerSheet(
    viewModel: SideMenuViewModel,
    drawerState: DrawerState,
    currentRoute: String?,
    onSideMenuItemSelected: (SideMenuItem) -> Unit,
    onChangeProfilePicture: () -> Unit
) {
    LaunchedEffect(Unit) {
        snapshotFlow { drawerState.isOpen }
            .collect { isOpen ->
                if (isOpen) {
                    viewModel.getData()
                }
            }
    }

    ModalDrawerSheet(
        drawerContainerColor = Colors.Black,
        drawerState = drawerState,
        content = {
            ViewStateCoordinator(
                state = viewModel.data,
                refresh = { viewModel.getData() },
                page = Page.SIDE_MENU
            ) { data ->
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ProfilePicture(
                        profilePictureUrl = data.profilePicture,
                        profileBackGroundPictureUrl = data.backGroundPicture,
                        onEditBackgroundPicture = onChangeProfilePicture,
                        onEditProfilePicture = onChangeProfilePicture
                    )

                    data.items.forEach { item ->
                        NavigationDrawerItem(
                            modifier = Modifier.padding(horizontal = pd16),
                            label = {
                                Text(
                                    text = stringResource(item.label),
                                    color = Colors.White,
                                    style = LifeHubTypography.labelLarge
                                )
                            },
                            selected = item.route == currentRoute,
                            icon = {
                                Icon(
                                    painter = painterResource(item.icon),
                                    contentDescription = stringResource(item.label),
                                    tint = Colors.Lavender
                                )
                            },
                            onClick = { onSideMenuItemSelected(item) },
                            badge = {
                                Icon(
                                    painter = painterResource(CoreR.drawable.ic_chevron),
                                    contentDescription = stringResource(item.label),
                                    tint = Colors.White
                                )
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color.DarkGray
                            )
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun AppBar(
    label: String,
    openSideMenu: () -> Unit,
    navBack: () -> Unit,
    endContent: @Composable RowScope. () -> Unit,
    hasNavDrawer: Boolean
) {
    Row(
        modifier = Modifier.background(Colors.Black),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { if (hasNavDrawer) openSideMenu() else navBack() }
        ) {
            Icon(
                painter = painterResource(
                    if (hasNavDrawer) {
                        R.drawable.ic_menu
                    } else {
                        R.drawable.ic_left_chevron
                    }
                ),
                contentDescription = stringResource(R.string.menu),
                tint = Colors.White
            )
        }

        Text(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            text = label,
            style = LifeHubTypography.labelLarge,
            color = Colors.White
        )

        endContent.invoke(this)
    }
}

@Preview
@Composable
private fun PreviewContent() {
    Content(
        startDestination = Page.DASHBOARD_HOME.route,
        appBarController = AppBarController(),
        onSignOut = {}
    )
}