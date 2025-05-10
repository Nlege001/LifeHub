package com.example.lifehub.features.dashboard.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.composables.GlideWrapper
import com.example.core.composables.TextButtonWithIcon
import com.example.core.composables.ViewStateCoordinator
import com.example.core.utils.baseHorizontalMargin
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd12
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd20
import com.example.core.values.Dimens.pd32
import com.example.core.values.Dimens.pd4
import com.example.core.values.Dimens.pd8
import com.example.lifehub.features.dashboard.home.viewmodel.ArticleListViewModel
import com.example.lifehub.network.articles.data.Article
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private val page = Page.DASHBOARD_HOME

@Composable
fun ArticleSection(
    viewModel: ArticleListViewModel = hiltViewModel(),
    onArticleClick: (String) -> Unit,
    onViewAll: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(pd16)
            .fillMaxWidth()
            .height(650.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Colors.DarkPurple,
                    )
                ),
                shape = RoundedCornerShape(pd20)
            ),
        shape = RoundedCornerShape(pd20),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(pd8),
    ) {
        ViewStateCoordinator(
            state = viewModel.articles,
            page = page,
            refresh = { viewModel.getArticles() }
        ) {
            NewsCardSection(
                title = "Current News",
                articles = it,
                onArticleClick = onArticleClick,
                onViewAll = onViewAll
            )
        }
    }
}

@Composable
fun NewsCardSection(
    title: String,
    articles: List<Article>,
    modifier: Modifier = Modifier,
    onArticleClick: (String) -> Unit,
    onViewAll: () -> Unit
) {
    Column(modifier = modifier.baseHorizontalMargin(pd32)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(bottom = pd8)
                    .weight(1f),
                color = Colors.White
            )

            TextButtonWithIcon(
                label = "View all"
            ) { onViewAll() }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(pd12)) {
            items(articles) { article ->
                NewsListItem(
                    article = article,
                    onArticleClick = onArticleClick
                )
            }
        }
    }
}

@Composable
fun NewsListItem(
    article: Article,
    onArticleClick: (String) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(pd4),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = { onArticleClick(article.link) })
    ) {
        Row(modifier = Modifier.padding(pd8)) {
            GlideWrapper(
                imageUrl = article.image_url ?: "",
                contentDescription = article.title,
                modifier = Modifier
                    .width(84.dp)
                    .height(84.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(pd12))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Colors.White
                )

                Text(
                    text = article.subtitleLine(),
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Colors.White
                )
            }
        }
    }
}

fun Article.subtitleLine(): String {
    val author = creator?.firstOrNull()
    val date = pubDate?.let {
        try {
            OffsetDateTime.parse(it).format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
        } catch (e: Exception) {
            null
        }
    }

    return listOfNotNull(author, date, source_name).joinToString(" • ")
}