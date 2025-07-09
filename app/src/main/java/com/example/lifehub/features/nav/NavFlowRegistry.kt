package com.example.lifehub.features.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.core.analytics.Page
import com.example.lifehub.encryptedsharedpreferences.SecurePreferences
import com.example.lifehub.features.nav.navrouters.AuthNav
import com.example.lifehub.features.nav.navrouters.MainNav
import com.example.lifehub.features.nav.navrouters.QuestionarieNav
import com.example.lifehub.network.auth.AuthService
import javax.inject.Inject

class NavFlowRegistry @Inject constructor(
    private val authNav: AuthNav,
    private val questionarieNav: QuestionarieNav,
    private val mainNav: MainNav,
    private val securePreferences: SecurePreferences,
    private val authService: AuthService
) {
    fun registerFlows(
        navController: NavHostController,
        builder: NavGraphBuilder
    ) {
        authNav.flow(
            navController = navController,
            builder = builder,
            startDestination = getAuthNavStartDestination()
        )
        questionarieNav.flow(
            navController = navController,
            builder = builder,
            startDestination = Page.FIRST_NAME_LAST_NAME
        )
        mainNav.flow(
            navController = navController,
            builder = builder,
            startDestination = Page.DASHBOARD_HOME
        )
    }

    private fun getAuthNavStartDestination(): Page {
        val currentUserId = authService.currentUserId() ?: return Page.LOGIN
        return if (securePreferences.hasPin(currentUserId)) {
            Page.VERIFY_PIN
        } else {
            Page.LOGIN
        }
    }
}