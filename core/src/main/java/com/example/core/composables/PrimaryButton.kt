package com.example.core.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.analytics.LocalAnalyticsLogger
import com.example.core.analytics.Page
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Dimens.pd12
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd20
import com.example.core.values.Dimens.pd24
import com.example.core.values.Dimens.pd48

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    label: String,
    screen: Page,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    enabled: Boolean = true,
) {
    val analytics = LocalAnalyticsLogger.current
    Button(
        modifier = modifier.height(pd48),
        enabled = enabled && !isLoading,
        onClick = {
            analytics.logCtaClicked(label, screen)
            onClick()
        },
        shape = RoundedCornerShape(pd12),
        content = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.height(pd24)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(pd20),
                    )
                } else {
                    Text(
                        text = label,
                        textAlign = TextAlign.Center,
                        style = LifeHubTypography.titleMedium
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewPrimaryButton() {
    Column(
        modifier = Modifier.padding(pd16),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            label = "Button",
            onClick = {},
            screen = Page.LOGIN
        )

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            label = "Button",
            isLoading = true,
            onClick = {},
            screen = Page.LOGIN
        )
    }
}