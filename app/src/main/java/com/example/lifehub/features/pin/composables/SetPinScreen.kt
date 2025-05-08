package com.example.lifehub.features.pin.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.example.core.analytics.Page
import com.example.core.analytics.TrackScreenSeen
import com.example.core.composables.MAX_PIN
import com.example.wpinterviewpractice.R

private val page = Page.SET_PIN

@Composable
fun SetPinScreen(
    onDone: (String) -> Unit
) {
    TrackScreenSeen(page)
    val pin = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(pin.value) {
        if (pin.value.length == MAX_PIN) {
            onDone(pin.value)
        }
    }

    GenericPinComposable(
        value = pin.value,
        onTextChange = { pin.value = it },
        isError = false,
        title = stringResource(R.string.set_pin_title)
    )
}