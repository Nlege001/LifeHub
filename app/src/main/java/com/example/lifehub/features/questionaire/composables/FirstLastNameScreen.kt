package com.example.lifehub.features.questionaire.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.analytics.Page
import com.example.core.analytics.TrackScreenSeen
import com.example.core.composables.CinematicTypingText
import com.example.core.composables.LogoAndAppTitle
import com.example.core.composables.OutLinedTextField
import com.example.core.composables.TextButtonWithIcon
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.baseHorizontalMargin
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16
import com.example.wpinterviewpractice.R

private val page = Page.FIRST_NAME_LAST_NAME

@Composable
fun FirstLastNameScreen(
    onDone: (String, String?) -> Unit
) {
    TrackScreenSeen(page)
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
        LogoAndAppTitle(
            modifier = Modifier.padding(top = pd16)
        )

        Spacer(modifier = Modifier.weight(1f))

        CinematicTypingText(
            modifier = Modifier.padding(pd16),
            text = stringResource(R.string.name_screen_title),
            textAlign = TextAlign.Center,
            color = Colors.White,
            style = LifeHubTypography.titleLarge
        )

        val keyboardController = LocalSoftwareKeyboardController.current
        val firstName = rememberSaveable { mutableStateOf("") }
        val shouldValidateInput = rememberSaveable { mutableStateOf(false) }
        val isFirstNameError =
            remember { derivedStateOf { firstName.value.isEmpty() && shouldValidateInput.value } }
        OutLinedTextField(
            modifier = Modifier.padding(vertical = pd16),
            label = stringResource(R.string.first_name),
            value = firstName.value,
            onValueChange = {
                firstName.value = it
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_first_name),
                    contentDescription = stringResource(R.string.first_name),
                    tint = Colors.White
                )
            },
            errorLabel = if (isFirstNameError.value) stringResource(R.string.first_name_error) else null,
            isError = isFirstNameError.value,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        val lastName = rememberSaveable { mutableStateOf("") }
        OutLinedTextField(
            label = stringResource(R.string.last_name_optional),
            value = lastName.value,
            onValueChange = {
                lastName.value = it
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_last_name),
                    contentDescription = stringResource(R.string.last_name_optional),
                    tint = Colors.White
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
            ),
            keyboardActions = KeyboardActions {
                keyboardController?.hide()
                shouldValidateInput.value = true
                if (firstName.value.isNotEmpty()) {
                    onDone(
                        firstName.value,
                        if (lastName.value.isEmpty()) null else lastName.value
                    )
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButtonWithIcon(
            modifier = Modifier.padding(vertical = pd16),
            label = stringResource(R.string.next),
            onClick = {
                shouldValidateInput.value = true

                if (firstName.value.isNotEmpty()) {
                    onDone(
                        firstName.value,
                        if (lastName.value.isEmpty()) null else lastName.value
                    )
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFirstLastNameScreen() {
    FirstLastNameScreen(
        onDone = { _, _ -> }
    )
}