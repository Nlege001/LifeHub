package com.example.core.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.example.core.values.Dimens

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

fun Modifier.baseHorizontalMargin(): Modifier {
    return this.padding(horizontal = Dimens.pd16)
}