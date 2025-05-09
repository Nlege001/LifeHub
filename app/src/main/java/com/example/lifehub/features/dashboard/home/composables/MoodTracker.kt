package com.example.lifehub.features.dashboard.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieConstants
import com.example.core.analytics.Page
import com.example.core.composables.BaseSlider
import com.example.core.composables.CinematicTypingText
import com.example.core.composables.LottieWrapper
import com.example.core.composables.OutLinedTextField
import com.example.core.composables.SelectableRowGroup
import com.example.core.composables.TextButtonWithIcon
import com.example.core.composables.ViewStateCoordinator
import com.example.core.data.PostResult
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.formatTimestamp
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd100
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd20
import com.example.core.values.Dimens.pd32
import com.example.core.values.Dimens.pd4
import com.example.core.values.Dimens.pd8
import com.example.lifehub.features.dashboard.home.data.Mood
import com.example.lifehub.features.dashboard.home.viewmodel.MoodViewModel
import com.example.lifehub.network.data.MoodEntry
import com.example.wpinterviewpractice.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MoodTracker() {
    val viewModel: MoodViewModel = hiltViewModel()
    val selectedItem = rememberSaveable { mutableStateOf<Mood?>(null) }
    val reflection = rememberSaveable { mutableStateOf<String?>(null) }
    var intensity by rememberSaveable { mutableStateOf(0.5f) }
    var moodSavedMessageVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .padding(pd16)
            .fillMaxWidth()
            .height(650.dp),
        shape = RoundedCornerShape(pd20),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(pd8),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Colors.DarkPurple,
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            ViewStateCoordinator(
                modifier = Modifier.fillMaxSize(),
                state = viewModel.moods,
                post = viewModel.postResult,
                isLoading = viewModel.isLoading,
                page = Page.DASHBOARD_HOME,
                refresh = { viewModel.getMoods() }
            ) { data, postResult, isLoading ->
                LaunchedEffect(postResult) {
                    postResult?.let {
                        if (it is PostResult.Success) {
                            reflection.value = null
                            selectedItem.value = null
                            intensity = 0.5f
                            moodSavedMessageVisible = true
                        }
                    }
                }

                val pagerState = rememberPagerState(pageCount = { 2 })

                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(pd16),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.weight(1f)
                        ) { page ->
                            when (page) {
                                0 -> Mood(
                                    modifier = Modifier.fillMaxSize(),
                                    selectedItem = selectedItem.value,
                                    updateSelectedItem = { selectedItem.value = it },
                                    intensity = intensity,
                                    updateIntensity = { intensity = it },
                                    reflection = reflection.value,
                                    updateReflection = { reflection.value = it },
                                    isLoading = isLoading,
                                    saveMood = viewModel::saveMood,
                                    moodSavedMessageVisible = moodSavedMessageVisible,
                                    removeSavedMessage = {
                                        scope.launch {
                                            delay(2000)
                                            moodSavedMessageVisible = false
                                        }
                                    }
                                )

                                1 -> MoodEntries(
                                    modifier = Modifier.fillMaxSize(),
                                    items = data.entries
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = pd8),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(2) { index ->
                                val isSelected = pagerState.currentPage == index
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .height(8.dp)
                                        .width(if (isSelected) 24.dp else 8.dp)
                                        .background(
                                            color = if (isSelected) Colors.Lavender else Color.Gray.copy(
                                                alpha = 0.5f
                                            ),
                                            shape = RoundedCornerShape(50)
                                        )
                                )
                            }
                        }
                    }

                    data.streak?.let {
                        if (it.shouldShowModal) {
                            MoodStreakBlur(
                                count = it.currentStreak,
                                onDone = { viewModel.markStreakModalShown() }
                            )
                        }
                    }

                    data.streak?.let {
                        MoodStreak(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            count = it.currentStreak,
                            subtitle = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Mood(
    modifier: Modifier,
    selectedItem: Mood?,
    updateSelectedItem: (Mood) -> Unit,
    intensity: Float,
    updateIntensity: (Float) -> Unit,
    reflection: String?,
    updateReflection: (String) -> Unit,
    isLoading: Boolean,
    saveMood: (Mood, String?, Float) -> Unit,
    moodSavedMessageVisible: Boolean,
    removeSavedMessage: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(pd16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.mood_tracker),
                style = LifeHubTypography.titleLarge,
                color = Color.White
            )

            LottieWrapper(
                modifier = Modifier.height(pd100),
                file = R.raw.anim_mood,
                iterations = LottieConstants.IterateForever
            )
        }

        Text(
            text = selectedItem?.label?.let { stringResource(it) }
                ?: stringResource(R.string.your_mood_today),
            style = LifeHubTypography.labelLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        SelectableRowGroup(
            modifier = Modifier.padding(pd8),
            items = Mood.entries,
            selectedItem = selectedItem,
            onItemClick = updateSelectedItem,
            boxContent = { mood -> Text(text = mood.emoji, style = LifeHubTypography.displayLarge) }
        )

        BaseSlider(value = intensity, onValueChange = updateIntensity)

        Text(
            text = stringResource(R.string.want_to_reflect_more),
            style = LifeHubTypography.bodyLarge,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        OutLinedTextField(
            label = stringResource(R.string.few_words),
            value = reflection.orEmpty(),
            onValueChange = updateReflection,
            singleLine = false,
            labelColor = Color.DarkGray
        )

        TextButtonWithIcon(
            modifier = Modifier.padding(pd8),
            label = stringResource(R.string.submit_mood),
            isLoading = isLoading,
            onClick = {
                selectedItem?.let {
                    saveMood(it, reflection, intensity)
                }
            },
            painter = null
        )

        if (moodSavedMessageVisible) {
            CinematicTypingText(
                text = stringResource(R.string.entry_saved),
                style = LifeHubTypography.bodyMedium,
                color = Colors.Lavender,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(pd8),
                onDone = removeSavedMessage
            )
        }
    }
}

@Composable
private fun MoodEntries(
    modifier: Modifier = Modifier,
    items: List<MoodEntry>
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(pd16),
        verticalArrangement = Arrangement.spacedBy(pd16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.mood_modal_title),
            style = LifeHubTypography.titleMedium,
            color = Color.White
        )

        if (items.isEmpty()) {
            Text(
                text = stringResource(R.string.no_entries),
                style = LifeHubTypography.bodyLarge,
                color = Color.LightGray
            )
        } else {
            items.forEachIndexed { index, entry ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = pd8)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${entry.mood.emoji} ${stringResource(id = entry.mood.label)}",
                            style = LifeHubTypography.labelLarge,
                            color = Color.White
                        )
                        Text(
                            text = entry.timestamp.formatTimestamp(),
                            style = LifeHubTypography.labelSmall,
                            color = Color.Gray
                        )
                    }

                    entry.reflection?.let {
                        Text(
                            text = it,
                            style = LifeHubTypography.bodySmall,
                            color = Color.LightGray,
                            modifier = Modifier.padding(top = pd4)
                        )
                    }

                    LinearProgressIndicator(
                        progress = entry.intensity,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = pd8)
                            .height(6.dp),
                        color = Colors.Lavender,
                        backgroundColor = Color.DarkGray
                    )

                    if (index != items.lastIndex) {
                        Divider(color = Color.Gray.copy(alpha = 0.2f))
                    }

                    if (index == items.lastIndex) {
                        Spacer(modifier = Modifier.height(pd32))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMood() {
    Mood(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        selectedItem = null,
        updateIntensity = {},
        updateReflection = {},
        updateSelectedItem = {},
        intensity = 0.5f,
        reflection = "",
        isLoading = false,
        saveMood = { _, _, _ -> },
        moodSavedMessageVisible = true,
        removeSavedMessage = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewMoodEntries() {
    val mockMoodEntries = listOf(
        MoodEntry(Mood.HAPPY, "Great day!", 0.8f, System.currentTimeMillis()),
        MoodEntry(Mood.SAD, "Tough morning", 0.4f, System.currentTimeMillis())
    )

    MoodEntries(
        modifier = Modifier.background(Color.Black),
        items = mockMoodEntries
    )
}