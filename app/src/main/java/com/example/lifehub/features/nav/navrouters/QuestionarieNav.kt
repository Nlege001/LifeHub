package com.example.lifehub.features.nav.navrouters

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.core.analytics.NavFlows
import com.example.core.analytics.Page
import com.example.lifehub.features.nav.NavRouter
import com.example.lifehub.features.questionaire.composables.AccountCreationSuccessScreen
import com.example.lifehub.features.questionaire.composables.DobScreen
import com.example.lifehub.features.questionaire.composables.FirstLastNameScreen
import javax.inject.Inject

class QuestionarieNav @Inject constructor() : NavRouter {

    override fun startFlow(
        navController: NavHostController,
    ) {
        navController.navigate(NavFlows.QUESTIONAIRE.route)
    }

    override fun flow(
        navController: NavHostController,
        onFlowComplete: () -> Unit,
        builder: NavGraphBuilder
    ) {
        builder.navigation(
            startDestination = Page.FIRST_NAME_LAST_NAME.route,
            route = NavFlows.QUESTIONAIRE.route,
        ) {
            composable(
                route = Page.FIRST_NAME_LAST_NAME.route,
            ) {
                FirstLastNameScreen { firstName, lastName ->
                    navController.navigate(Page.DOB.buildRoute(firstName, lastName))
                }
            }
            composable(
                route = Page.DOB.route,
                arguments = listOf(
                    navArgument(name = "firstName") {
                        type = NavType.StringType
                    },
                    navArgument(name = "lastName") {
                        type = NavType.StringType
                        nullable = true
                    },
                )
            ) {
                DobScreen(
                    onSuccess = { navController.navigate(Page.ACCOUNT_CREATION_SUCCESS.route) }
                )
            }
            composable(
                route = Page.ACCOUNT_CREATION_SUCCESS.route
            ) {
                AccountCreationSuccessScreen { onFlowComplete }
            }
        }
    }

}