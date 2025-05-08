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
import com.example.core.data.PostResult
import com.example.lifehub.features.pin.viewmodel.DisablePinViewModel
import com.example.wpinterviewpractice.R

private val page = Page.DISABLE_PIN

@Composable
fun DisablePinScreen(
    viewModel: DisablePinViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {
    TrackScreenSeen(page)
    val pin = rememberSaveable { mutableStateOf("") }
    val isError = viewModel.isError.collectAsState().value
    val clearPin = viewModel.clearPin.collectAsState().value

    val pinText = if (isError == true) {
        stringResource(R.string.incorrect_pin)
    } else {
        null
    }

    LaunchedEffect(clearPin) {
        if (clearPin is PostResult.Success) {
            onSuccess()
        }
    }

    LaunchedEffect(isError) {
        if (isError == false) {
            viewModel.clearPin()
        }
    }

    LaunchedEffect(pin.value) {
        if (pin.value.length == MAX_PIN) {
            viewModel.verifyPin(pin.value)
        }
    }

    GenericPinComposable(
        value = pin.value,
        onTextChange = { pin.value = it },
        isError = isError == true,
        title = stringResource(R.string.please_provide_old_code_disable),
        pinText = pinText
    )
}