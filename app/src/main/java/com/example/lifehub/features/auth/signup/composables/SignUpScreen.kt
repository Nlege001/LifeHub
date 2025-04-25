package com.example.lifehub.features.auth.signup.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.analytics.TrackScreenSeen
import com.example.core.composables.LogoAndAppTitle
import com.example.core.composables.OutLinedTextField
import com.example.core.composables.PrimaryButton
import com.example.core.data.PostResult
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.InputValidator
import com.example.core.utils.baseHorizontalMargin
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd12
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd32
import com.example.core.values.Dimens.pd8
import com.example.lifehub.features.auth.signup.data.PasswordChecks
import com.example.lifehub.features.auth.signup.viewmodel.SignUpViewModel
import com.example.wpinterviewpractice.R

private val page = Page.SIGN_UP

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    done: () -> Unit,
) {
    TrackScreenSeen(page)
    val postResult = viewModel.postResult.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value

    LaunchedEffect(postResult) {
        if (postResult is PostResult.Success) {
            done()
        }
    }

    Content(
        onRequestSignUp = { email, password -> viewModel.signUp(email, password) },
        creationError = postResult == PostResult.Error(),
        isLoading = isLoading,
    )
}

@Composable
private fun Content(
    creationError: Boolean,
    onRequestSignUp: (email: String, password: String) -> Unit,
    isLoading: Boolean,
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
        LogoAndAppTitle(textColor = Colors.White)


        Text(
            modifier = Modifier.padding(vertical = pd16),
            text = stringResource(R.string.sign_up),
            style = LifeHubTypography.titleLarge,
            color = Colors.White
        )

        val email = rememberSaveable { mutableStateOf<String>("") }
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

        val password = rememberSaveable { mutableStateOf<String>("") }
        val shouldShowPassword = rememberSaveable { mutableStateOf(false) }
        val isPasswordError = remember {
            derivedStateOf { !InputValidator.isPasswordValid(password.value) && shouldValidateInput.value }
        }
        OutLinedTextField(
            modifier = Modifier
                .padding(bottom = pd32)
                .fillMaxWidth(),
            value = password.value,
            label = stringResource(R.string.password),
            onValueChange = {
                password.value = it
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_password),
                    contentDescription = stringResource(R.string.password),
                    tint = Color.White
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { shouldShowPassword.value = !shouldShowPassword.value }
                ) {
                    Icon(
                        painter = painterResource(
                            if (shouldShowPassword.value) {
                                R.drawable.ic_hide_password
                            } else {
                                R.drawable.ic_show_password
                            }
                        ),
                        contentDescription = stringResource(R.string.password),
                        tint = Color.White
                    )
                }
            },
            visualTransformation = if (shouldShowPassword.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = isPasswordError.value,
            errorLabel = if (isPasswordError.value) stringResource(R.string.password_error) else null
        )

        ChecksRow(password.value)
        Spacer(modifier = Modifier.weight(1f))

        if (creationError) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(pd12))
                    .background(color = Color(0xFFFFE6E6))
                    .padding(vertical = pd12, horizontal = pd16)
            ) {
                Text(
                    text = stringResource(R.string.sign_up_error),
                    color = Color.Red,
                    style = LifeHubTypography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = pd16),
            label = stringResource(R.string.sign_up),
            onClick = {
                shouldValidateInput.value = true
                if (!isEmailError.value && !isPasswordError.value) {
                    onRequestSignUp(email.value, password.value)
                }
            },
            isLoading = isLoading,
            screen = page
        )
    }
}

@Composable
private fun ChecksRow(password: String) {
    val items = PasswordChecks.getAllChecks()
    items.forEachIndexed { index, item ->
        Check(
            password = password,
            check = item,
            showDivider = index != items.lastIndex
        )
    }
}

@Composable
private fun Check(
    modifier: Modifier = Modifier,
    password: String,
    check: PasswordChecks,
    showDivider: Boolean
) {
    val isValid = check.isValid(password)
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                painter = painterResource(
                    if (isValid) {
                        R.drawable.ic_check
                    } else {
                        R.drawable.ic_unchecked
                    }
                ),
                contentDescription = stringResource(check.checkLabel),
                tint = if (isValid) {
                    Color.Green
                } else {
                    Color.Gray
                }
            )

            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(check.checkLabel),
                style = if (isValid) {
                    LifeHubTypography.bodySmall.copy(
                        textDecoration = TextDecoration.LineThrough
                    )
                } else {
                    LifeHubTypography.bodySmall
                },
                color = Color.White
            )
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSignUpScreen() {
    Content(
        creationError = true,
        onRequestSignUp = { _, _ -> },
        isLoading = false
    )
}