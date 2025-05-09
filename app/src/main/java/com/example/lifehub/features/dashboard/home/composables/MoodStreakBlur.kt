package com.example.lifehub.features.dashboard.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.composables.CinematicTypingText
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.baseHorizontalMargin
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd64
import com.example.wpinterviewpractice.R

@Composable
fun MoodStreakBlur(
    modifier: Modifier = Modifier,
    count: Int,
    onDone: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color(0xFF1A1A1A).copy(alpha = 0.5f))
                .blur(pd64)
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MoodStreak(
                count = count,
                subtitle = null
            )

            CinematicTypingText(
                modifier = Modifier.baseHorizontalMargin(),
                text = stringResource(R.string.streak),
                textAlign = TextAlign.Center,
                style = LifeHubTypography.titleMedium,
                color = Colors.White,
                onDone = onDone
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
fun PreviewMoodStreakBlur() {
    MoodStreakBlur(
        count = 3,
        onDone = {}
    )
}