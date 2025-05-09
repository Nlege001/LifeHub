package com.example.core.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import com.example.core.values.Dimens
import com.example.core.values.Dimens.pd0

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

fun Modifier.setAlpha(condition: Boolean): Modifier {
    return this
        .conditional(condition) { alpha(1f) }
        .conditional(!condition) { alpha(0f) }
}

fun Modifier.baseHorizontalMargin(verticalPadding: Dp = pd0): Modifier {
    return this.padding(
        horizontal = Dimens.pd16,
        vertical = verticalPadding
    )
}