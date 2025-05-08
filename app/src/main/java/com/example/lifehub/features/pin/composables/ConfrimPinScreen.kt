package com.example.lifehub.features.pin.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.analytics.TrackScreenSeen
import com.example.core.composables.MAX_PIN
import com.example.core.data.PostResult
import com.example.lifehub.features.pin.viewmodel.ConfirmPinViewModel
import com.example.wpinterviewpractice.R

private val page = Page.CONFIRM_PIN

@Composable
fun ConfirmPinScreen(
    viewModel: ConfirmPinViewModel = hiltViewModel(),
    onDone: () -> Unit
) {
    TrackScreenSeen(page)
    val pin = rememberSaveable { mutableStateOf("") }
    val isError = remember {
        derivedStateOf {
            if (pin.value.length == MAX_PIN) {
                pin.value != viewModel.pin
            } else {
                false
            }
        }
    }

    val postState = viewModel.setPin.collectAsState().value
    LaunchedEffect(postState) {
        if (postState is PostResult.Success) {
            onDone()
        }
    }

    LaunchedEffect(pin.value) {
        if (pin.value.length == MAX_PIN && !isError.value) {
            viewModel.setPin(pin.value)
        }
    }

    val pinText = if (isError.value) {
        stringResource(R.string.pins_dont_match)
    } else {
        null
    }
    GenericPinComposable(
        value = pin.value,
        onTextChange = { pin.value = it },
        isError = isError.value,
        title = stringResource(R.string.confirm_pin),
        pinText = pinText
    )
}