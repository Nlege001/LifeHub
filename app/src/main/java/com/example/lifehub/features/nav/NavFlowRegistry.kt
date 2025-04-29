package com.example.lifehub.features.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import javax.inject.Inject

class NavFlowRegistry @Inject constructor(
    private val authNav: AuthNav,
    private val questionarieNav: QuestionarieNav
) {
    fun registerFlows(
        navController: NavHostController,
        builder: NavGraphBuilder
    ) {
        authNav.flow(
            navController = navController,
            onFlowComplete = { questionarieNav.startFlow(navController) },
            builder = builder
        )
        questionarieNav.flow(
            navController = navController,
            onFlowComplete = {},
            builder = builder
        )
    }
}