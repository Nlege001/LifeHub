package com.example.core.composables

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.core.analytics.Page
import com.example.core.analytics.TrackScreenSeen
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.baseHorizontalMargin
import com.example.core.values.Colors
import com.example.core.values.Dimens
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd32
import com.example.core.values.Dimens.pd8

@Composable
fun GenericSuccessScreen(
    page: Page,
    @RawRes lottie: Int,
    @StringRes header: Int,
    @StringRes body: Int,
    @StringRes ctaText: Int,
    onCtaClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Colors.Black)
            .baseHorizontalMargin()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TrackScreenSeen(page)
        LogoAndAppTitle()

        LottieWrapper(
            modifier = Modifier.size(Dimens.pd300),
            file = lottie
        )

        CinematicTypingText(
            modifier = Modifier
                .padding(top = pd16, bottom = pd8)
                .fillMaxWidth(),
            text = stringResource(header),
            color = Colors.White,
            style = LifeHubTypography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .padding(top = pd16, bottom = pd8)
                .fillMaxWidth(),
            text = stringResource(body),
            color = Colors.White,
            style = LifeHubTypography.bodySmall,
            textAlign = TextAlign.Center
        )

        TextButtonWithIcon(
            modifier = Modifier.padding(bottom = pd32),
            label = stringResource(ctaText),
            onClick = onCtaClick
        )
    }
}