package com.example.lifehub.features.article

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.composables.ViewStateCoordinator
import com.example.core.values.Dimens.pd8
import com.example.lifehub.features.dashboard.home.composables.NewsListItem
import com.example.lifehub.features.dashboard.home.viewmodel.ArticlesViewModel
import com.example.lifehub.network.articles.data.Article

@Composable
fun ArticleListScreen(
    viewModel: ArticlesViewModel = hiltViewModel(),
    onArticleClick: (String) -> Unit
) {
    ViewStateCoordinator(
        state = viewModel.articles,
        page = Page.ARTICLE_LIST,
        refresh = { viewModel.getArticles() },
        content = {
            Content(
                data = it,
                onArticleClick = onArticleClick
            )
        }
    )
}

@Composable
private fun Content(
    data: List<Article>,
    onArticleClick: (String) -> Unit
) {
    LazyColumn {
        items(data) {
            Spacer(modifier = Modifier.height(pd8))
            NewsListItem(
                article = it,
                onArticleClick = onArticleClick
            )
            Spacer(modifier = Modifier.height(pd8))
        }
    }
}