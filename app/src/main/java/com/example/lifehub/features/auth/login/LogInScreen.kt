package com.example.lifehub.features.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.analytics.TrackScreenSeen
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
import com.example.core.values.Dimens.pd8
import com.example.lifehub.user.UserViewModel
import com.example.wpinterviewpractice.R

private val page = Page.LOGIN

@Composable
fun LogInScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onSignInSuccessful: (Boolean) -> Unit,
    navToSignUp: () -> Unit,
    forgotPassword: (String) -> Unit
) {
    TrackScreenSeen(page)
    val postResult = viewModel.postResult.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val userViewModel: UserViewModel = hiltViewModel()
    val isQuestionaireComplete =
        userViewModel.user.collectAsState().value?.hasCompletedQuestionaire == true

    LaunchedEffect(postResult) {
        if (postResult is PostResult.Success) {
            onSignInSuccessful(isQuestionaireComplete)
        }
    }

    Content(
        onRequestSignIn = { email, password -> viewModel.signIn(email, password) },
        signInError = postResult is PostResult.Error,
        isLoading = isLoading,
        navToSignUp = navToSignUp,
        forgotPassword = forgotPassword
    )
}

@Composable
private fun Content(
    signInError: Boolean,
    onRequestSignIn: (email: String, password: String) -> Unit,
    isLoading: Boolean,
    navToSignUp: () -> Unit,
    forgotPassword: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Colors.Black)
            .baseHorizontalMargin()
            .imePadding()
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
            text = stringResource(R.string.login),
            style = LifeHubTypography.titleLarge,
            color = Colors.White
        )

        val email = rememberSaveable { mutableStateOf<String>("") }
        val shouldValidateInput = rememberSaveable { mutableStateOf(false) }
        val isEmailError = remember {
            derivedStateOf {
                !InputValidator.isValidEmail(email.value.trim()) && shouldValidateInput.value
            }
        }
        val keyboardController = LocalSoftwareKeyboardController.current
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
            errorLabel = if (isEmailError.value) stringResource(R.string.email_error) else null,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        val password = rememberSaveable { mutableStateOf<String>("") }
        val shouldShowPassword = rememberSaveable { mutableStateOf(false) }
        val isPasswordError = remember {
            derivedStateOf { !InputValidator.isPasswordValid(password.value) && shouldValidateInput.value }
        }
        OutLinedTextField(
            modifier = Modifier
                .padding(bottom = pd16)
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
            errorLabel = if (isPasswordError.value) stringResource(R.string.password_error) else null,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
            ),
            keyboardActions = KeyboardActions {
                keyboardController?.hide()
                shouldValidateInput.value = true
                if (!isEmailError.value && !isPasswordError.value) {
                    onRequestSignIn(email.value.trim(), password.value)
                }
            }
        )

        TextButtonWithIcon(
            painter = null,
            label = stringResource(R.string.forgot_password),
            onClick = { forgotPassword(email.value) },
            textStyle = LifeHubTypography.bodySmall
        )

        if (signInError) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(pd12))
                    .background(color = Color(0xFFFFE6E6))
                    .padding(vertical = pd12, horizontal = pd16)
            ) {
                Text(
                    text = stringResource(R.string.login_error),
                    color = Color.Red,
                    style = LifeHubTypography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        val annotatedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.White,
                )
            ) {
                append(stringResource(R.string.not_a_member))
                append(" ")
            }

            pushStringAnnotation(
                tag = "SIGN_UP",
                annotation = "sign_up"
            )
            withStyle(
                style = SpanStyle(
                    color = Color.White,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(stringResource(R.string.sign_up))
            }
            pop()
        }

        ClickableText(
            modifier = Modifier.padding(bottom = pd8),
            text = annotatedText,
            style = LifeHubTypography.bodySmall,
            onClick = { offset ->
                annotatedText.getStringAnnotations(tag = "SIGN_UP", start = offset, end = offset)
                    .firstOrNull()?.let {
                        navToSignUp()
                    }
            },
        )
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = pd16),
            label = stringResource(R.string.login),
            onClick = {
                shouldValidateInput.value = true
                if (!isEmailError.value && !isPasswordError.value) {
                    onRequestSignIn(email.value.trim(), password.value)
                }
            },
            isLoading = isLoading,
            screen = page
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogInScreen() {
    Content(
        signInError = false,
        onRequestSignIn = { _, _ -> },
        isLoading = false,
        navToSignUp = {},
        forgotPassword = {}
    )
}