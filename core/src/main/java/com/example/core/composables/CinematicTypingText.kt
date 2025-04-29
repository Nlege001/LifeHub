package com.example.core.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors
import kotlinx.coroutines.delay

@Composable
fun CinematicTypingText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Left,
    color: Color = Colors.White,
    style: TextStyle = LifeHubTypography.titleLarge,
    typingSpeedMillis: Long = 100L // Speed per letter
) {
    val visibleText = remember { mutableStateOf("") }

    LaunchedEffect(text) {
        visibleText.value = ""
        text.forEachIndexed { index, _ ->
            visibleText.value = text.substring(0, index + 1)
            delay(typingSpeedMillis)
        }
    }

    Text(
        text = visibleText.value,
        style = style,
        modifier = modifier,
        color = color,
        textAlign = textAlign
    )
}