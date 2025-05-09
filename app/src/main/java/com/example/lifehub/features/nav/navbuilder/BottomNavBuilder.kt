package com.example.lifehub.features.nav.navbuilder

import android.net.Uri
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.analytics.NavArgumentType
import com.example.core.analytics.Page
import com.example.lifehub.features.dashboard.home.composables.DashboardFeed
import com.example.lifehub.features.pin.composables.ConfirmPinScreen
import com.example.lifehub.features.pin.composables.DisablePinScreen
import com.example.lifehub.features.pin.composables.SetPinScreen
import com.example.lifehub.features.pin.composables.VerifyPinScreen
import com.example.lifehub.features.profile.ProfileScreen
import com.example.lifehub.features.progress.ProgressScreen
import com.example.wpinterviewpractice.R

/**
 * Nav builder for the bottom nav
 **/
fun NavGraphBuilder.bottomNavBuilder(
    navHostController: NavHostController,
    onSignOut: (String?) -> Unit
) {
    composable(Page.DASHBOARD_HOME.route) {
        DashboardFeed(
            onArticleClick = {
                val encodedUrl = Uri.encode(it)
                navHostController.navigate(Page.ARTICLE.buildRoute(encodedUrl))
            }
        )
    }
    composable(Page.POSTS.route) { }
    composable(Page.PROGRESS.route) {
        ProgressScreen(
            navigate = {}
        )
    }
    composable(Page.MESSAGES.route) { }
    composable(Page.PROFILE.route) {
        ProfileScreen(
            onSignOut = onSignOut,
            onSetPin = {
                navHostController.navigate(Page.SET_PIN.route)
            },
            onChangePin = {
                navHostController.navigate(Page.VERIFY_PIN.route)
            },
            onDisablePin = {
                navHostController.navigate(Page.DISABLE_PIN.route)
            }
        )
    }
    composable(Page.VERIFY_PIN.route) {
        VerifyPinScreen(
            title = stringResource(R.string.please_provide_old_code),
            onSuccess = {
                navHostController.navigate(Page.SET_PIN.route)
            }
        )
    }
    composable(Page.SET_PIN.route) {
        SetPinScreen(
            onDone = { pin ->
                navHostController.navigate(Page.CONFIRM_PIN.buildRoute(pin)) {
                    popUpTo(Page.SET_PIN.route) { inclusive = true }
                }
            }
        )
    }
    composable(
        route = Page.CONFIRM_PIN.route,
        arguments = listOf(
            navArgument(NavArgumentType.PIN.label) {
                type = NavType.StringType
            }
        )
    ) {
        ConfirmPinScreen(
            onDone = {
                navHostController.popBackStack(Page.PROFILE.route, false)
            }
        )
    }
    composable(
        route = Page.DISABLE_PIN.route
    ) {
        DisablePinScreen {
            navHostController.popBackStack(Page.PROFILE.route, false)
        }
    }

    featureNavBuilder(navHostController)
}