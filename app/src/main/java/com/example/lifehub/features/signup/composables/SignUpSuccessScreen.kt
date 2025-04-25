package com.example.lifehub.features.signup.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.analytics.Page
import com.example.core.analytics.TrackScreenSeen
import com.example.core.composables.LogoAndAppTitle
import com.example.core.composables.LottieWrapper
import com.example.core.composables.TextButtonWithIcon
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.baseHorizontalMargin
import com.example.core.values.Colors
import com.example.core.values.Dimens
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd32
import com.example.core.values.Dimens.pd8
import com.example.wpinterviewpractice.R

private val page = Page.SIGN_UP_SUCCESS

@Composable
fun SignUpSuccessScreen(
    getStarted: () -> Unit
) {
    TrackScreenSeen(page)
    Column(
        modifier = Modifier
            .background(Colors.Black)
            .baseHorizontalMargin()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoAndAppTitle()

        LottieWrapper(
            modifier = Modifier.size(Dimens.pd400),
            file = R.raw.anim_done
        )

        Text(
            modifier = Modifier
                .padding(top = pd16, bottom = pd8)
                .fillMaxWidth(),
            text = stringResource(R.string.sign_up_success),
            color = Colors.White,
            style = LifeHubTypography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .padding(top = pd16, bottom = pd8)
                .fillMaxWidth(),
            text = stringResource(R.string.sign_up_success_message),
            color = Colors.White,
            style = LifeHubTypography.bodySmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButtonWithIcon(
            modifier = Modifier.padding(bottom = pd32),
            label = stringResource(R.string.get_started),
            onClick = getStarted
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpSuccessScreen() {
    SignUpSuccessScreen(
        getStarted = {}
    )
}