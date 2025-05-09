package com.example.lifehub.features.dashboard.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.composables.ViewStateCoordinator
import com.example.lifehub.features.dashboard.home.DashboardFeedData
import com.example.lifehub.features.dashboard.home.viewmodel.DashboardFeedViewModel
import com.example.lifehub.network.quoteoftheday.data.QuoteOfTheDay
import kotlinx.coroutines.delay

private val page = Page.DASHBOARD_HOME

@Composable
fun DashboardFeed(
    viewModel: DashboardFeedViewModel = hiltViewModel(),
    onArticleClick: (String) -> Unit
) {
    val greetingShown = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!greetingShown.value) {
            delay(200)
            greetingShown.value = true
        }
    }

    ViewStateCoordinator(
        state = viewModel.feedData,
        refresh = { viewModel.getData() },
        page = page
    ) {
        Content(it, greetingShown.value, onArticleClick)
    }

}

@Composable
private fun Content(
    data: DashboardFeedData,
    showGreeting: Boolean,
    onArticleClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxSize()
    ) {
        data.greeting?.let {
            item { WelcomeUserCard(it, showGreeting) }
        }

        data.quoteOfTheDay?.let {
            item {
                QuoteOfTheDayCard(it)
            }
        }

        if (data.showMoodTracker) {
            item { MoodTracker() }
        }

        item {
            ArticleSection(
                onArticleClick = onArticleClick
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDashboardFeed() {
    Content(
        data = DashboardFeedData(
            greeting = "Have a good day Naol!",
            quoteOfTheDay = QuoteOfTheDay(
                q = "The mind is everything. What you think you become.",
                a = "Buddha",
                h = ""
            ),
            showMoodTracker = true,
        ),
        showGreeting = true,
        onArticleClick = {}
    )
}