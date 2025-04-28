package com.example.core.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.core.R
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Dimens

enum class IconLocation {
    START,
    END
}

@Composable
fun TextButtonWithIcon(
    modifier: Modifier = Modifier,
    label: String,
    painter: Painter? = painterResource(R.drawable.ic_chevron),
    iconEndPadding: Dp = Dimens.pd0,
    textStyle: TextStyle = LifeHubTypography.bodyLarge,
    textColor: Color = Color.White,
    iconTint: Color = Color.White,
    iconSize: Dp = Dimens.pd32,
    iconLocation: IconLocation = IconLocation.END,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        enabled = !isLoading && enabled,
        content = {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.height(iconSize),
                    color = textColor
                )
            } else {
                if (iconLocation == IconLocation.START) {
                    painter?.let {
                        Icon(
                            modifier = Modifier
                                .size(iconSize)
                                .padding(end = iconEndPadding),
                            painter = painter,
                            contentDescription = label,
                            tint = iconTint
                        )
                    }
                }

                Text(
                    text = label,
                    style = textStyle.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = textColor
                )

                if (iconLocation == IconLocation.END) {
                    painter?.let {
                        Icon(
                            modifier = Modifier
                                .size(iconSize)
                                .padding(start = iconEndPadding),
                            painter = painter,
                            contentDescription = label,
                            tint = iconTint
                        )
                    }
                }
            }
        },
        onClick = onClick
    )
}

@Preview
@Composable
fun PreviewTextButtonWithIcon() {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButtonWithIcon(
            label = "Text button",
            onClick = {}
        )

        TextButtonWithIcon(
            label = "Text button",
            onClick = {},
            isLoading = true
        )
    }
}