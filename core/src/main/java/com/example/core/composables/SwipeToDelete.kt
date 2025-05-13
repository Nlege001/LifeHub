package com.example.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun SwipeToDeleteItem(
    itemKey: Any,
    content: @Composable () -> Unit,
    onDelete: () -> Unit,
) {
    var offsetX by remember(key1 = itemKey) { mutableFloatStateOf(0f) }
    val maxOffset = with(LocalDensity.current) { 100.dp.toPx() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Red),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(onClick = { onDelete() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            // Snap logic: if passed halfway, snap open
                            offsetX = if (offsetX < -maxOffset / 2) -maxOffset else 0f
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            val newOffset = (offsetX + dragAmount).coerceIn(-maxOffset, 0f)
                            offsetX = newOffset
                        }
                    )
                }
                .background(Color.DarkGray)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSwipeToDelete() {
    SwipeToDeleteItem(
        content = {
            Text(
                text = "Swipe me left",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                color = Color.Black
            )
        },
        onDelete = { /* remove item from list */ },
        itemKey = ""
    )
}