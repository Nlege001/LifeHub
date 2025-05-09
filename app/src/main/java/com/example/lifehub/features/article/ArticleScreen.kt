package com.example.lifehub.features.article

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.analytics.Page
import com.example.core.composables.WebView

val page = Page.ARTICLE

@Composable
fun ArticleScreen(
    url: String
) {
    WebView(
        modifier = Modifier.fillMaxSize(),
        url = url,
        page = page
    )
}