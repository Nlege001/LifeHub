package com.example.lifehub.features.nav.navbuilder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.core.analytics.Page
import com.example.lifehub.features.dashboard.home.composables.DashboardFeed
import com.example.lifehub.features.profile.ProfileScreen
import com.example.lifehub.features.progress.ProgressScreen

/**
 * Nav builder for the bottom nav
 **/
fun NavGraphBuilder.bottomNavBuilder(
    navHostController: NavHostController
) {
    composable(Page.DASHBOARD_HOME.route) {
        DashboardFeed()
    }
    composable(Page.POSTS.route) { }
    composable(Page.PROGRESS.route) {
        ProgressScreen(
            navigate = {}
        )
    }
    composable(Page.MESSAGES.route) { }
    composable(Page.PROFILE.route) {
        ProfileScreen()
    }
}