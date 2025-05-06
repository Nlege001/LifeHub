package com.example.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd4
import com.example.core.values.Dimens.pd48
import com.example.core.values.Dimens.pd8

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SelectableRowGroup(
    modifier: Modifier = Modifier,
    items: List<T>,
    selectedItem: T?,
    onItemClick: (T) -> Unit,
    boxContent: @Composable (T) -> Unit,
    content: @Composable (T) -> Unit = { item ->
        SelectableSquareBox(
            item = item,
            selectedItem = selectedItem,
            onClick = onItemClick,
            boxContent = boxContent
        )

    },
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(pd4)
    ) {
        items.forEach {
            content.invoke(it)
        }
    }
}

@Composable
fun <T> SelectableSquareBox(
    item: T,
    selectedItem: T?,
    onClick: (T) -> Unit,
    boxContent: @Composable (T) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (item == selectedItem) Colors.Lavender else Color.LightGray

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(pd48)
            .clip(RoundedCornerShape(pd8))
            .background(Colors.Black)
            .border(pd4, borderColor, RoundedCornerShape(pd8))
            .clickable { onClick(item) }
    ) {
        boxContent(item)
    }
}