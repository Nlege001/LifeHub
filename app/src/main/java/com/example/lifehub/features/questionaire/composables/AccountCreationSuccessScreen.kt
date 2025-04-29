package com.example.lifehub.features.questionaire.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.analytics.Page
import com.example.core.composables.GenericSuccessScreen
import com.example.wpinterviewpractice.R

private val page = Page.ACCOUNT_CREATION_SUCCESS

@Composable
fun AccountCreationSuccessScreen(
    onDone: () -> Unit
) {
    GenericSuccessScreen(
        page = page,
        lottie = R.raw.anim_account_success,
        header = R.string.welcome,
        body = R.string.welcome_body,
        ctaText = R.string.lets_go,
        onCtaClick = onDone
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewAccountCreationSuccessScreen() {
    AccountCreationSuccessScreen(
        onDone = {}
    )
}