package com.example.lifehub.features.todo

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.analytics.TrackScreenSeen
import com.example.core.composables.SwipeToDeleteItem
import com.example.core.composables.dragdrop.DragDropList
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd64
import com.example.wpinterviewpractice.R

private val page = Page.TODO

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TodoList(
    viewModel: TodoViewModel = hiltViewModel()
) {
    val items = viewModel.items.collectAsState().value
    val listState = rememberLazyListState()

    val isScrolling by remember {
        derivedStateOf { listState.isScrollInProgress }
    }

    TrackScreenSeen(page)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            DragDropList(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize(),
                items = items,
                onMove = { from, to -> viewModel.move(from, to) },
                itemContent = { item, _, index ->
                    SwipeToDeleteItem(
                        content = {
                            Todo(
                                item = item,
                                onTextChange = { text -> viewModel.updateText(item.id, text) },
                                onCheckedChange = { checked ->
                                    viewModel.updateChecked(
                                        item.id,
                                        checked
                                    )
                                }
                            )
                        },
                        onDelete = {
                            viewModel.deleteItem(item.id)
                        },
                        itemKey = item.id
                    )

                },
                listState = listState
            )
        },
        floatingActionButton = {
            AnimatedVisibility(!isScrolling) {
                IconButton(
                    onClick = { viewModel.addItem() },
                    modifier = Modifier
                        .size(pd64)
                        .clip(CircleShape)
                        .background(Colors.Lavender),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_task),
                        contentDescription = "",
                        tint = Colors.White
                    )
                }
            }
        }
    )
}