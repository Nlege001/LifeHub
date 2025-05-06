package com.example.core.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    steps: Int = 8,
    modifier: Modifier = Modifier
) {
    val animatedValue by animateFloatAsState(targetValue = value, label = "SliderAnimation")
    val stepCount = steps + 1
    val tickValues = (0..stepCount).map { it.toFloat() / stepCount }

    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            tickValues.forEachIndexed { index, _ ->
                Text(
                    text = "${index + 1}",
                    style = LifeHubTypography.labelMedium,
                    color = if (animatedValue >= index.toFloat() / stepCount) Colors.Lavender else Color.White
                )
            }
        }

        Slider(
            value = animatedValue,
            onValueChange = onValueChange,
            steps = steps,
            valueRange = 0f..1f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFB497F0),
                activeTrackColor = Color(0xFFB497F0),
                inactiveTrackColor = Color.DarkGray
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewBaseSlider() {
    var intensity by rememberSaveable { mutableStateOf(0.5f) }

    BaseSlider(
        value = intensity,
        onValueChange = { intensity = it }
    )
}