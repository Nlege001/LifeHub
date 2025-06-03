package com.example.core.composables.dragdrop

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.core.R
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd32
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun <T> DragDropList(
    items: List<T>,
    onMove: (Int, Int) -> Unit,
    topContent: @Composable () -> Unit = {},
    itemContent: @Composable (T, Boolean, Int) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var overscrollJob by remember { mutableStateOf<Job?>(null) }
    val dragState = remember { DragDropState(listState, onMove) }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectDragGesturesAfterLongPress(
                        onDragStart = dragState::onDragStart,
                        onDragEnd = dragState::onDragEnd,
                        onDragCancel = dragState::onDragEnd,
                        onDrag = { change, offset ->
                            change.consumeAllChanges()
                            dragState.onDrag(offset)

                            if (overscrollJob?.isActive != true) {
                                dragState.checkForOverScroll()?.let {
                                    overscrollJob = scope.launch {
                                        listState.scrollBy(it)
                                    }
                                } ?: overscrollJob?.cancel()
                            }
                        }
                    )
                }
        ) {
            item { topContent() }

            if (items.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize()
                                .padding(pd16),
                            text = stringResource(R.string.add_items),
                            textAlign = TextAlign.Center,
                            color = Color.LightGray,
                            style = LifeHubTypography.labelLarge
                        )
                    }
                }
            } else {
                itemsIndexed(items) { index, item ->
                    val isDragging = dragState.draggedIndex == index

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                if (isDragging) {
                                    translationY = dragState.draggedOffset
                                }
                            }
                            .zIndex(if (isDragging) 1f else 0f)
                            .padding(8.dp)
                            .background(
                                if (isDragging) Color.LightGray else Color.DarkGray,
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        itemContent(item, isDragging, index)
                        if (items.lastIndex == index) {
                            Spacer(modifier = Modifier.height(pd32))
                        }
                    }
                }
            }
        }
    }
}

fun <T> MutableList<T>.move(from: Int, to: Int) {
    if (from == to || from !in indices || to !in indices) return
    val item = removeAt(from)
    add(to, item)
}

@Preview(showBackground = true)
@Composable
fun PreviewDragDropList() {
    val items = remember { mutableStateListOf("One", "Two", "Three", "Four", "Five") }

    DragDropList(
        items = items,
        onMove = { from, to -> items.move(from, to) },
        itemContent = { item, _, _ ->
            Text(
                text = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        },
        listState = rememberLazyListState()
    )
}