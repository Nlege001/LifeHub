package com.example.lifehub.features.moodhabits

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.composables.ViewStateCoordinator
import com.example.core.composables.graphs.BaseBarChart
import com.example.core.composables.graphs.BasePieChart
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.baseHorizontalMargin
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd200
import com.example.core.values.Dimens.pd24
import com.example.core.values.Dimens.pd300
import com.example.core.values.Dimens.pd32
import com.example.core.values.Dimens.pd8
import com.example.lifehub.features.moodhabits.data.DayMood.Companion.generateDayMoods
import com.example.lifehub.features.moodhabits.data.WeekDays
import com.example.lifehub.network.data.MoodEntry
import com.example.wpinterviewpractice.R
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.piechart.PieChartData
import java.time.LocalDate

@Composable
fun MoodHabitsScreen(
    viewModel: MoodHabitsViewModel = hiltViewModel()
) {
    ViewStateCoordinator(
        state = viewModel.state,
        refresh = { viewModel.getMoodEntries() },
        page = Page.MOOD
    ) {
        Content(data = it)
    }
}

@Composable
private fun Content(data: List<MoodEntry>) {
    val moodMap = data.groupBy { it.mood }
    val total = data.sumOf { it.intensity.toDouble() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        item {
            Text(
                modifier = Modifier
                    .baseHorizontalMargin()
                    .padding(vertical = pd16),
                text = stringResource(R.string.mood_summary),
                color = Colors.White,
                style = LifeHubTypography.displayLarge
            )
        }

        item {
            Row(
                modifier = Modifier
                    .baseHorizontalMargin()
                    .fillMaxWidth()
                    .padding(bottom = pd24),
                horizontalArrangement = Arrangement.spacedBy(pd32),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasePieChart(
                    modifier = Modifier.size(pd200),
                    data = moodMap.map { (key, value) ->
                        val moodTotalIntensity = value.sumOf { it.intensity.toDouble() }
                        PieChartData.Slice(
                            value = ((moodTotalIntensity * 100) / total).toFloat(),
                            color = key.color,
                        )
                    }
                )

                Column(verticalArrangement = Arrangement.spacedBy(pd8)) {
                    moodMap.keys.forEach {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(pd8)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(pd8)
                                    .clip(CircleShape)
                                    .background(it.color),
                            )

                            Text(
                                text = stringResource(it.label) + " " + it.emoji,
                                style = LifeHubTypography.bodyMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = stringResource(R.string.mood_bar_chart),
                modifier = Modifier
                    .baseHorizontalMargin()
                    .padding(bottom = pd8),
                color = Color.White,
                style = LifeHubTypography.titleLarge
            )
        }

        item {
            BaseBarChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(pd300)
                    .padding(bottom = pd24),
                data = moodMap.map {
                    BarChartData.Bar(
                        value = it.value.size.toFloat(),
                        color = it.key.color,
                        label = it.key.emoji
                    )
                },
            )
        }

        item {
            MoodHeatmap(data)
        }
    }
}

@Composable
fun MoodHeatmap(data: List<MoodEntry>) {
    val past30Days = (0..29).map { LocalDate.now().minusDays(it.toLong()) }.reversed()
    val days = generateDayMoods(data, past30Days)

    // Ensure list is multiple of 7
    val fullWeeks = (days.size / 7) * 7
    val trimmedDays = days.takeLast(fullWeeks)
    val weeks = trimmedDays.chunked(7)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = pd16, vertical = pd8)
    ) {
        Text(
            text = stringResource(R.string.mood_heatmap),
            style = LifeHubTypography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = pd8)
        )

        // Weekday headers
        Row(horizontalArrangement = Arrangement.spacedBy(pd8)) {
            WeekDays.entries.toList().forEach { day ->
                Box(
                    modifier = Modifier
                        .width(pd32)
                        .padding(bottom = pd8),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(day.label).take(3),
                        style = LifeHubTypography.labelSmall,
                        color = Color.LightGray
                    )
                }
            }
        }

        // Mood bubbles
        weeks.forEach { week ->
            Row(
                modifier = Modifier.padding(bottom = pd8),
                horizontalArrangement = Arrangement.spacedBy(pd8),
            ) {
                week.forEach { day ->
                    val color = day.mood?.color ?: Color.Gray
                    val emoji = day.mood?.emoji ?: ""

                    Box(
                        modifier = Modifier
                            .size(pd32)
                            .clip(CircleShape)
                            .background(color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(emoji, style = LifeHubTypography.bodySmall)
                    }
                }
            }
        }
    }
}