package com.example.lifehub.features.auth.signup.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.analytics.TrackScreenSeen
import com.example.core.composables.LogoAndAppTitle
import com.example.core.composables.LottieWrapper
import com.example.core.composables.TextButtonWithIcon
import com.example.core.data.PostResult
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.baseHorizontalMargin
import com.example.core.utils.openEmailApp
import com.example.core.values.Colors
import com.example.core.values.Dimens
import com.example.core.values.Dimens.pd12
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd32
import com.example.core.values.Dimens.pd8
import com.example.lifehub.features.auth.signup.viewmodel.EmailVerificationViewModel
import com.example.wpinterviewpractice.R

private val page = Page.EMAIL_VERIFICATION

@Composable
fun EmailVerificationScreen(
    viewModel: EmailVerificationViewModel = hiltViewModel(),
) {
    TrackScreenSeen(page)
    val postResult = viewModel.postResult.collectAsState().value
    val isResendLoading = viewModel.isResendLoading.collectAsState().value

    Content(
        hasRequestFailed = postResult == PostResult.Error(),
        isResendLoading = isResendLoading,
        onResend = { viewModel.resendVerification() }
    )
}

@Composable
private fun Content(
    hasRequestFailed: Boolean,
    isResendLoading: Boolean,
    onResend: () -> Unit
) {
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
            modifier = Modifier.size(Dimens.pd300),
            file = R.raw.anim_email_verification
        )

        Text(
            modifier = Modifier
                .padding(top = pd16, bottom = pd8)
                .fillMaxWidth(),
            text = stringResource(R.string.email_verification),
            color = Colors.White,
            style = LifeHubTypography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .padding(top = pd16, bottom = pd8)
                .fillMaxWidth(),
            text = stringResource(R.string.email_verification_body),
            color = Colors.White,
            style = LifeHubTypography.bodySmall,
            textAlign = TextAlign.Center
        )

        if (hasRequestFailed) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(pd12))
                    .background(color = Color(0xFFFFE6E6))
                    .padding(vertical = pd12, horizontal = pd16)
            ) {
                Text(
                    text = stringResource(R.string.email_verification_error),
                    color = Color.Red,
                    style = LifeHubTypography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        TextButtonWithIcon(
            modifier = Modifier.padding(bottom = pd32),
            label = stringResource(R.string.resend),
            onClick = onResend,
            isLoading = isResendLoading
        )

        val context = LocalContext.current
        ClickableText(
            modifier = Modifier.padding(
                top = pd16,
                bottom = pd8
            ),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.White,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(stringResource(R.string.open_mail_app))
                }
            },
            style = LifeHubTypography.bodySmall,
            onClick = { context.openEmailApp() },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmailVerificationScreen() {
    Content(
        onResend = {},
        isResendLoading = false,
        hasRequestFailed = false
    )
}