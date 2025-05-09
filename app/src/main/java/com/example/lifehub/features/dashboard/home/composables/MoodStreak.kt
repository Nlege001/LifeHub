package com.example.lifehub.features.dashboard.home.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieConstants
import com.example.core.composables.LottieWrapper
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd100
import com.example.core.values.Dimens.pd4
import com.example.core.values.Dimens.pd8
import com.example.wpinterviewpractice.R

@Composable
fun MoodStreak(
    modifier: Modifier = Modifier,
    count: Int,
    countColor: Color = Colors.White,
    subtitle: String? = stringResource(R.string.streak_subtitle)
) {
    Box(modifier = modifier) {
        LottieWrapper(
            modifier = Modifier
                .size(pd100),
            file = R.raw.anim_streak,
            iterations = LottieConstants.IterateForever
        )

        Text(
            modifier = Modifier
                .offset(y = -pd4)
                .align(Alignment.CenterStart),
            text = count.toString(),
            style = LifeHubTypography.displayLarge,
            color = countColor
        )

        subtitle?.let {
            Text(
                modifier = Modifier
                    .offset(y = -pd8)
                    .align(Alignment.BottomStart),
                text = it,
                style = LifeHubTypography.labelLarge,
                color = countColor
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
fun PreviewMoodStreak() {
    MoodStreak(
        count = 3,
        countColor = Colors.Black
    )
}