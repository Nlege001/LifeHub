package com.example.lifehub.features.auth.signup.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.analytics.Page
import com.example.core.composables.GenericSuccessScreen
import com.example.wpinterviewpractice.R

private val page = Page.SIGN_UP_SUCCESS

@Composable
fun SignUpSuccessScreen(
    getStarted: () -> Unit
) {
    GenericSuccessScreen(
        page = page,
        lottie = R.raw.anim_done,
        header = R.string.sign_up_success,
        body = R.string.sign_up_success_message,
        ctaText = R.string.get_started,
        onCtaClick = getStarted
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpSuccessScreen() {
    SignUpSuccessScreen(
        getStarted = {}
    )
}