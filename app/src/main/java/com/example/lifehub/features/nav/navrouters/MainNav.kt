package com.example.lifehub.features.nav.navrouters

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.core.analytics.NavFlows
import com.example.core.analytics.Page
import com.example.lifehub.features.dashboard.MainScreen
import com.example.lifehub.features.nav.NavRouter
import javax.inject.Inject

/**
 * Nav router for screens that comes after the user signs in successfully
 **/
class MainNav @Inject constructor() : NavRouter {
    override fun startFlow(navController: NavHostController) {
        navController.navigate(NavFlows.MAIN.route) {
            launchSingleTop = true
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
                inclusive = true
            }
            restoreState = true
        }
    }

    override fun flow(
        navController: NavHostController,
        builder: NavGraphBuilder
    ) {
        builder.composable(NavFlows.MAIN.route) {
            MainScreen(
                startDestination = Page.DASHBOARD_HOME.route,
                onSignOut = {
                    navController.navigate(NavFlows.AUTH.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}