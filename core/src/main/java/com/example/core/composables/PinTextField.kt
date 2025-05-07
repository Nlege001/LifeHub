package com.example.core.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd1
import com.example.core.values.Dimens.pd2
import com.example.core.values.Dimens.pd32
import com.example.core.values.Dimens.pd4
import com.example.core.values.Dimens.pd48
import kotlin.math.roundToInt

val MAX_PIN = 6

@Composable
fun PinTextField(
    value: String,
    onTextChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean = false,
    shouldHidePassword: Boolean = false
) {
    val borderColor = if (isError) Color.Red else Colors.Lavender

    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(isError) {
        if (isError) {
            val pattern = listOf(-10f, 10f, -8f, 8f, -5f, 5f, 0f)
            for (value in pattern) {
                shakeOffset.animateTo(value, animationSpec = tween(30))
            }
        }
    }

    Box(
        modifier = Modifier.offset { IntOffset(shakeOffset.value.roundToInt(), 0) }
    ) {
        BasicTextField(
            value = value,
            onValueChange = {
                if (it.length <= MAX_PIN) onTextChange(it)
            },
            enabled = enabled,
            decorationBox = { innerTextField ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(pd4)
                ) {
                    repeat(MAX_PIN) { index ->
                        Box(
                            modifier = Modifier
                                .border(pd1, borderColor, RoundedCornerShape(pd2))
                                .size(pd48),
                            contentAlignment = Alignment.Center
                        ) {
                            val char = value.getOrNull(index)?.toString() ?: ""

                            if (index == value.length && value.length < MAX_PIN) {
                                // This will place the actual text field in the "active" box
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .align(Alignment.Center)
                                ) {
                                    innerTextField()
                                }
                            } else {
                                Text(
                                    text = if (shouldHidePassword && char.isNotEmpty()) "." else char,
                                    color = Color.White,
                                    style = LifeHubTypography.displayLarge
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewPinTextField() {
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
    PinTextField(
        value = text.value,
        onTextChange = { text.value = it },
        shouldHidePassword = true,
        isError = isError.value
    )
}