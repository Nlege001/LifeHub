package com.example.lifehub.features.dashboard.home.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.composables.CinematicTypingText
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd20
import com.example.core.values.Dimens.pd24
import com.example.core.values.Dimens.pd4
import com.example.core.values.Dimens.pd8
import com.example.wpinterviewpractice.R

@Composable
fun WelcomeUserCard(
    greeting: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = pd16, vertical = pd8)
    ) {
        Box {
            // Main bubble
            Surface(
                shape = RoundedCornerShape(pd24),
                color = Color(0xFFB497F0),
                elevation = pd4,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(pd24),
                    verticalArrangement = Arrangement.spacedBy(pd8)
                ) {
                    CinematicTypingText(
                        text = greeting,
                        style = LifeHubTypography.labelLarge,
                        color = Color.Black
                    )

                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = stringResource(R.string.life_hub),
                        style = LifeHubTypography.labelSmall,
                        color = Color.DarkGray
                    )
                }
            }

            // Triangle tail
            Canvas(
                modifier = Modifier
                    .size(pd20)
                    .align(Alignment.BottomStart)
                    .offset(x = pd8, y = pd8)
            ) {
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width / 2, size.height)
                    lineTo(size.width, 0f)
                    close()
                }
                drawPath(path, color = Color(0xFFB497F0))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewWelcomeUserCard() {
    WelcomeUserCard(greeting = "Have a wonderful day Naol!")
}