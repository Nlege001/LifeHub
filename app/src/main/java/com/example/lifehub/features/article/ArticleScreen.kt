package com.example.lifehub.features.article

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.core.analytics.Page
import com.example.core.composables.WebView
import com.example.core.utils.shareLink
import com.example.lifehub.features.dashboard.home.appbar.AppBarIcon
import com.example.lifehub.features.dashboard.home.appbar.LocalAppBarController
import com.example.lifehub.features.dashboard.home.appbar.SetButtons
import com.example.wpinterviewpractice.R

val page = Page.ARTICLE

@Composable
fun ArticleScreen(
    url: String
) {
    val appBar = LocalAppBarController.current
    val context = LocalContext.current

    appBar.SetButtons(
        listOf(
            AppBarIcon(
                iconResId = R.drawable.ic_share,
                contentDescription = "Share article"
            ) {
                context.shareLink(url)
            }
        )
    )

    WebView(
        modifier = Modifier.fillMaxSize(),
        url = url,
        page = page
    )
}