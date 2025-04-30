package com.example.lifehub.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.analytics.NavFlows
import com.example.core.analytics.Page
import com.example.core.composables.AppLogo
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd100
import com.example.core.values.Dimens.pd16
import com.example.lifehub.features.dashboard.bottomnav.BottomNavigationBar
import com.example.lifehub.features.dashboard.sidemenu.SideMenuItem
import com.example.lifehub.features.nav.navbuilder.bottomNavBuilder
import com.example.lifehub.features.nav.navbuilder.sideMenuNavBuilder
import com.example.wpinterviewpractice.R
import kotlinx.coroutines.launch
import com.example.core.R as CoreR

@Composable
fun MainScreen(startDestination: String = Page.DASHBOARD_HOME.route) {
    Content(startDestination = startDestination)
}

@Composable
private fun Content(
    startDestination: String
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
            DrawerSheet(
                drawerState = drawerState,
                currentRoute = currentRoute,
                onSideMenuItemSelected = { item ->
                    scope.launch { drawerState.close() }
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(NavFlows.BOTTOM_NAV.route) {
                            saveState = true
                        }
                    }
                }
            )
        },
        content = {
            Scaffold(
                topBar = {
                    AppBar(
                        label = currentPageLabel,
                        openSideMenu = { scope.launch { drawerState.open() } }
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
                        route = NavFlows.BOTTOM_NAV.route,
                        builder = {
                            bottomNavBuilder(navController)
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
    drawerState: DrawerState,
    currentRoute: String?,
    onSideMenuItemSelected: (SideMenuItem) -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = Colors.Black,
        drawerState = drawerState,
        content = {
            Column(
                modifier = Modifier
                    .padding(horizontal = pd16)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AppLogo(
                    modifier = Modifier.size(pd100)
                )

                SideMenuItem.getSideMenuItems().forEach { item ->
                    NavigationDrawerItem(
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
    )
}

@Composable
private fun AppBar(
    label: String,
    openSideMenu: () -> Unit
) {
    Row(
        modifier = Modifier.background(Colors.Black),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = openSideMenu
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_menu),
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
    }
}

@Preview
@Composable
private fun PreviewDrawerSheet() {
    DrawerSheet(
        drawerState = rememberDrawerState(DrawerValue.Closed),
        currentRoute = Page.JOURNAL.route,
        onSideMenuItemSelected = {}
    )
}

@Preview
@Composable
private fun PreviewContent() {
    Content(startDestination = Page.DASHBOARD_HOME.route)
}