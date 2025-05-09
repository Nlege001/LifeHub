package com.example.lifehub.features.nav.navbuilder

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.core.analytics.NavArgumentType
import com.example.core.analytics.Page
import com.example.lifehub.features.article.ArticleScreen

fun NavGraphBuilder.featureNavBuilder(
    navHostController: NavHostController,
) {
    composable(
        route = Page.ARTICLE.route,
        arguments = listOf(
            navArgument(NavArgumentType.URL.label) {
                type = NavType.StringType
            }
        )
    ) { entry ->
        val rawUrl = entry.arguments?.getString(NavArgumentType.URL.label)
        val decodedUrl = rawUrl?.let { Uri.decode(it) }
        decodedUrl?.let {
            ArticleScreen(it)
        }
    }
}