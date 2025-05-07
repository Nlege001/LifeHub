package com.example.lifehub.features.auth.pin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.composables.LogoAndAppTitle
import com.example.core.composables.MAX_PIN
import com.example.core.composables.PinTextField
import com.example.core.utils.baseHorizontalMargin
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd32
import com.example.core.values.Dimens.pd48
import com.example.core.values.Dimens.pd8
import com.example.wpinterviewpractice.R

@Composable
fun GenericPinComposable(
    modifier: Modifier = Modifier,
    value: String,
    onTextChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    title: String,
    pinText: String?
) {
    val showPassword = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(Colors.Black)
            .fillMaxSize()
            .baseHorizontalMargin(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoAndAppTitle(
            modifier = Modifier.padding(top = pd32)
        )

        Text(
            modifier = Modifier.padding(vertical = pd48),
            text = title,
            color = Colors.White,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PinTextField(
                value = value,
                onTextChange = onTextChange,
                enabled = enabled,
                isError = isError,
                shouldHidePassword = showPassword.value
            )

            IconButton(
                onClick = { showPassword.value = !showPassword.value }
            ) {
                Icon(
                    painter = painterResource(
                        if (showPassword.value) {
                            R.drawable.ic_hide_password
                        } else {
                            R.drawable.ic_show_password
                        }
                    ),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }

        pinText?.let {
            Text(
                modifier = Modifier.padding(top = pd8),
                text = it,
                color = Color.Red,
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun PreviewGenericPinComposable() {
    val pin = "111111"
    val text = remember { mutableStateOf("") }
    val isError = remember {
        derivedStateOf {
            if (text.value.length == MAX_PIN) {
                text.value != pin
            } else {
                false
            }
        }
    }

    GenericPinComposable(
        value = text.value,
        onTextChange = { text.value = it },
        isError = isError.value,
        title = "Please provide your six digit pin",
        pinText = if (isError.value) {
            "Pin is incorrect"
        } else null
    )
}