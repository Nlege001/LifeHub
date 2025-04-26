package com.example.lifehub.features.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.analytics.Page
import com.example.core.composables.GenericSuccessScreen
import com.example.wpinterviewpractice.R

private val page = Page.PASSWORD_RESET_SUCCESS

@Composable
fun PasswordResetSuccessScreen(
    backToLogIn: () -> Unit
) {
    GenericSuccessScreen(
        page = page,
        lottie = R.raw.anim_done,
        header = R.string.password_reset_success,
        body = R.string.password_reset_success_body,
        ctaText = R.string.back_to_login,
        onCtaClick = backToLogIn
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewPasswordResetSuccessScreen() {
    PasswordResetSuccessScreen(
        backToLogIn = {}
    )
}