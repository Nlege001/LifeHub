package com.example.lifehub.features.pin.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.analytics.TrackScreenSeen
import com.example.core.composables.MAX_PIN
import com.example.lifehub.features.pin.viewmodel.VerifyPinViewModel
import com.example.wpinterviewpractice.R

private val page = Page.VERIFY_PIN

@Composable
fun VerifyPinScreen(
    title: String = stringResource(R.string.confirm_pin),
    viewModel: VerifyPinViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {
    TrackScreenSeen(page)

    val pin = rememberSaveable { mutableStateOf("") }
    val isError = viewModel.isError.collectAsState().value

    LaunchedEffect(isError) {
        if (isError == false) {
            onSuccess()
        }
    }

    LaunchedEffect(pin.value) {
        if (pin.value.length == MAX_PIN) {
            viewModel.verifyPin(pin.value)
        }
    }

    val pinText = if (isError == true) {
        stringResource(R.string.incorrect_pin)
    } else {
        null
    }

    GenericPinComposable(
        value = pin.value,
        onTextChange = { pin.value = it },
        isError = isError == true,
        title = title,
        pinText = pinText
    )
}