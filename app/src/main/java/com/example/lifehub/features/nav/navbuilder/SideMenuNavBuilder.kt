package com.example.lifehub.features.nav.navbuilder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.core.analytics.Page

/**
 * Nav router for the side navigation drawer
 **/
fun NavGraphBuilder.sideMenuNavBuilder(
    navHostController: NavHostController
) {
    composable(Page.JOURNAL.route) { }
    composable(Page.REFLECTIONS.route) {}
    composable(Page.INSIGHTS.route) { }
    composable(Page.CALENDAR.route) { }
    composable(Page.INVITE_A_FRIEND.route) { }
    composable(Page.SETTINGS.route) { }
    composable(Page.SIGN_OUT.route) { }
}