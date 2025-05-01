package com.example.lifehub.features.nav.navbuilder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.core.analytics.Page
import com.example.lifehub.features.dashboard.home.composables.DashboardFeed

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
    composable(Page.PROGRESS.route) { }
    composable(Page.MESSAGES.route) { }
    composable(Page.PROFILE.route) { }
}