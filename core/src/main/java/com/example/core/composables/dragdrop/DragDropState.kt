package com.example.core.composables.dragdrop

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

class DragDropState(
    private val listState: LazyListState,
    private val onMove: (Int, Int) -> Unit
) {
    var draggedOffset by mutableStateOf(0f)
        private set
    var draggedIndex by mutableStateOf<Int?>(null)
        private set
    private var initialItem: LazyListItemInfo? = null

    fun onDragStart(offset: Offset) {
        initialItem = listState.layoutInfo.visibleItemsInfo.firstOrNull {
            offset.y.toInt() in it.offset until (it.offset + it.size)
        }
        draggedIndex = initialItem?.index
    }

    fun onDrag(offset: Offset) {
        draggedOffset += offset.y
        val fromIndex = draggedIndex ?: return

        val currentItem = listState.layoutInfo.visibleItemsInfo
            .firstOrNull { it.index == fromIndex } ?: return

        val hoveredItem = listState.layoutInfo.visibleItemsInfo
            .filter { it.index != fromIndex }
            .firstOrNull {
                val range = it.offset until (it.offset + it.size)
                range.contains((currentItem.offset + draggedOffset).toInt())
            }

        hoveredItem?.let {
            onMove(fromIndex, it.index)
            draggedIndex = it.index
            draggedOffset = 0f
        }
    }

    fun onDragEnd() {
        draggedIndex = null
        draggedOffset = 0f
        initialItem = null
    }

    fun checkForOverScroll(): Float? {
        val start = initialItem?.offset?.plus(draggedOffset) ?: return null
        val end = (initialItem?.offset ?: 0) + (initialItem?.size ?: 0) + draggedOffset

        return when {
            draggedOffset > 0 -> (end - listState.layoutInfo.viewportEndOffset).takeIf { it > 0 }
            draggedOffset < 0 -> (start - listState.layoutInfo.viewportStartOffset).takeIf { it < 0 }
            else -> null
        }
    }
}