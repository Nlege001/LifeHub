package com.example.lifehub.features.todo

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.composables.DatePicker
import com.example.core.composables.SwipeToDeleteItem
import com.example.core.composables.ViewStateCoordinator
import com.example.core.composables.dragdrop.DragDropList
import com.example.core.data.PostResult
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd32
import com.example.core.values.Dimens.pd8
import com.example.lifehub.features.todo.data.TodoData
import com.example.wpinterviewpractice.R
import com.example.core.R as CoreR

private val page = Page.TODO

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TodoListScreen(
    viewModel: TodoViewModel = hiltViewModel(),
    navBack: () -> Unit
) {
    val postResult = viewModel.postResult.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val errorMessage = remember {
        mutableStateOf<String?>(null)
    }

    val message = stringResource(CoreR.string.generic_error)
    LaunchedEffect(postResult) {
        postResult?.let {
            when (it) {
                is PostResult.Success -> navBack()
                is PostResult.Error -> {
                    errorMessage.value = message
                }
            }
        }
    }

    ViewStateCoordinator(
        state = viewModel.items,
        refresh = { viewModel.getData() },
        page = page
    ) {
        Content(
            data = it,
            move = viewModel::move,
            updateText = viewModel::updateText,
            updateChecked = viewModel::updateChecked,
            deleteItem = viewModel::deleteItem,
            errorMessage = errorMessage.value,
            resetErrorMessage = { errorMessage.value = null },
            updateDate = viewModel::updateDate,
            save = viewModel::save,
            isLoading = isLoading,
            validateSave = viewModel::validateSave,
            addItem = viewModel::addItem
        )
    }
}

@Composable
private fun Content(
    data: TodoData,
    move: (Int, Int) -> Unit,
    updateText: (id: String, newText: String) -> Unit,
    updateChecked: (id: String, isChecked: Boolean) -> Unit,
    deleteItem: (String) -> Unit,
    errorMessage: String?,
    updateDate: (Long) -> Unit,
    save: () -> Unit,
    isLoading: Boolean,
    validateSave: () -> Boolean,
    addItem: () -> Unit,
    resetErrorMessage: () -> Unit
) {
    val listState = rememberLazyListState()

    val isScrolling by remember {
        derivedStateOf { listState.isScrollInProgress }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            DragDropList(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize(),
                items = data.items,
                onMove = { from, to -> move(from, to) },
                itemContent = { item, _, index ->
                    SwipeToDeleteItem(
                        content = {
                            Todo(
                                item = item,
                                onTextChange = { text -> updateText(item.id, text) },
                                onCheckedChange = { checked ->
                                    updateChecked(
                                        item.id,
                                        checked
                                    )
                                }
                            )
                        },
                        onDelete = {
                            deleteItem(item.id)
                        },
                        itemKey = item.id
                    )

                },
                listState = listState,
                topContent = {
                    Column(
                        modifier = Modifier.padding(pd16),
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = pd8),
                            text = stringResource(R.string.todo_screen_subtitle),
                            style = LifeHubTypography.titleLarge,
                            color = Color.LightGray
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier,
                                text = stringResource(R.string.todo_screen_title),
                                style = LifeHubTypography.titleSmall,
                                color = Color.LightGray
                            )
                        }

                        DatePicker(
                            modifier = Modifier.fillMaxWidth(),
                            selectedDate = data.date,
                            onDateSelected = { updateDate(it) },
                            label = stringResource(R.string.todo_date)
                        )
                    }
                }
            )

            if (errorMessage != null) {
                AlertDialog(
                    onDismissRequest = { resetErrorMessage() },
                    confirmButton = {
                        Button(onClick = { resetErrorMessage() }) {
                            Text("OK")
                        }
                    },
                    text = {
                        Text(errorMessage ?: "")
                    },
                    containerColor = Color.White,
                    titleContentColor = Color.Red,
                    textContentColor = Color.Black
                )
            }
        },
        floatingActionButton = {
            Column {
                AnimatedVisibility(!isScrolling) {
                    IconButton(
                        onClick = { addItem() },
                        modifier = Modifier
                            .size(pd32)
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

                Spacer(Modifier.padding(pd16))

                AnimatedVisibility(validateSave()) {
                    IconButton(
                        enabled = !isLoading,
                        onClick = { save() },
                        modifier = Modifier
                            .size(pd32)
                            .clip(CircleShape)
                            .background(Colors.Lavender),
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator()
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.ic_save),
                                contentDescription = "",
                                tint = Colors.White
                            )
                        }
                    }
                }
            }
        }
    )
}