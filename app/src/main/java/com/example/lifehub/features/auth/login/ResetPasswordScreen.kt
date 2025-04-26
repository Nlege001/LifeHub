package com.example.lifehub.features.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.composables.LogoAndAppTitle
import com.example.core.composables.OutLinedTextField
import com.example.core.composables.PrimaryButton
import com.example.core.composables.TextButtonWithIcon
import com.example.core.data.PostResult
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.InputValidator
import com.example.core.utils.baseHorizontalMargin
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd12
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd32
import com.example.core.values.Dimens.pd8
import com.example.lifehub.features.auth.signup.viewmodel.ResetEmailViewModel
import com.example.wpinterviewpractice.R

private val page = Page.PASSWORD_RESET

@Composable
fun ResetPasswordScreen(
    viewModel: ResetEmailViewModel = hiltViewModel(),
    email: String
) {
    val postResult = viewModel.postResult.collectAsState().value
    val isResendLoading = viewModel.isResendLoading.collectAsState().value

    Content(
        resetEmailError = postResult is PostResult.Error,
        email = email,
        sendPasswordResetEmail = { viewModel.sendPasswordResetEmail(it) },
        resendEmail = { viewModel.resendVerification(it) },
        isResendLoading = isResendLoading
    )
}

@Composable
private fun Content(
    resetEmailError: Boolean,
    email: String,
    sendPasswordResetEmail: (String) -> Unit,
    resendEmail: (String) -> Unit,
    isResendLoading: Boolean
) {
    Column(
        modifier = Modifier
            .background(Colors.Black)
            .baseHorizontalMargin()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        LogoAndAppTitle(textColor = Colors.White)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier.padding(bottom = pd16),
            text = stringResource(R.string.password_reset),
            style = LifeHubTypography.titleLarge,
            color = Colors.White
        )

        Text(
            modifier = Modifier.padding(bottom = pd16),
            text = stringResource(R.string.password_reset_body),
            style = LifeHubTypography.bodyMedium,
            color = Colors.White,
            textAlign = TextAlign.Center
        )

        val email = rememberSaveable { mutableStateOf<String>(email) }
        val shouldValidateInput = rememberSaveable { mutableStateOf(false) }
        val isEmailError = remember {
            derivedStateOf {
                !InputValidator.isValidEmail(email.value) && shouldValidateInput.value
            }
        }
        OutLinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = pd8),
            value = email.value,
            label = stringResource(R.string.email),
            onValueChange = {
                email.value = it
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_email),
                    contentDescription = stringResource(R.string.email),
                    tint = Color.White
                )
            },
            isError = isEmailError.value,
            errorLabel = if (isEmailError.value) stringResource(R.string.email_error) else null
        )

        if (resetEmailError) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(pd12))
                    .background(color = Color(0xFFFFE6E6))
                    .padding(vertical = pd12, horizontal = pd16)
            ) {
                Text(
                    text = stringResource(R.string.password_reset_error),
                    color = Color.Red,
                    style = LifeHubTypography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButtonWithIcon(
            modifier = Modifier.padding(bottom = pd32),
            label = stringResource(R.string.resend),
            onClick = { resendEmail(email.value) },
            isLoading = isResendLoading,
            textStyle = LifeHubTypography.bodySmall,
            iconSize = pd32
        )

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = pd16),
            label = stringResource(R.string.send_reset_email),
            onClick = {
                shouldValidateInput.value = true
                if (!isEmailError.value) {
                    sendPasswordResetEmail(email.value)
                }
            },
            screen = page
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewResetPasswordScreen() {
    Content(
        resetEmailError = false,
        email = "",
        sendPasswordResetEmail = {},
        resendEmail = {},
        isResendLoading = false
    )
}