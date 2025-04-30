package com.example.lifehub.features.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.lifehub.features.nav.navrouters.AuthNav
import com.example.lifehub.features.nav.navrouters.MainNav
import com.example.lifehub.features.nav.navrouters.QuestionarieNav
import javax.inject.Inject

class NavFlowRegistry @Inject constructor(
    private val authNav: AuthNav,
    private val questionarieNav: QuestionarieNav,
    private val mainNav: MainNav
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
            onFlowComplete = { mainNav.startFlow(navController) },
            builder = builder
        )
        mainNav.flow(
            navController = navController,
            builder = builder
        )
    }
}