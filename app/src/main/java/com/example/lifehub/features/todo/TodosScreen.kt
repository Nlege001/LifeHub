package com.example.lifehub.features.todo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.analytics.Page
import com.example.core.composables.ViewStateCoordinator
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.baseHorizontalMargin
import com.example.core.utils.toReadableDate
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd8
import com.example.lifehub.features.dashboard.home.appbar.AppBarIcon
import com.example.lifehub.features.dashboard.home.appbar.LocalAppBarController
import com.example.lifehub.features.dashboard.home.appbar.SetButtons
import com.example.lifehub.features.todo.data.TodoData
import com.example.lifehub.features.todo.data.TodoItem
import com.example.wpinterviewpractice.R

@Composable
fun TodosScreen(
    viewModel: TodosViewModel = hiltViewModel(),
    onTodoClick: (String) -> Unit,
    addTodo: () -> Unit
) {
    val appBar = LocalAppBarController.current
    appBar.SetButtons(
        listOf(
            AppBarIcon(
                iconResId = R.drawable.ic_add_task,
                contentDescription = "Add todo"
            ) {
                addTodo()
            }
        )
    )

    LaunchedEffect(Unit) {
        viewModel.getData()
    }

    ViewStateCoordinator(
        state = viewModel.data,
        refresh = { viewModel.getData() },
        page = Page.TODO_LIST
    ) {
        Content(
            data = it,
            onTodoClick = onTodoClick,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    data: List<TodoData>,
    onTodoClick: (String) -> Unit,
) {
    val dateToItemsMap = data.associate { it.date to (it.id to it.items) }

    LazyColumn(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxSize()
    ) {
        if (data.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(pd16),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Click the add button on the app bar to create a todo list",
                        style = LifeHubTypography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            dateToItemsMap.forEach { (date, pair) ->
                val (todoId, items) = pair

                stickyHeader {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Colors.Lavender),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                date?.toReadableDate()?.let {
                                    Text(
                                        text = it,
                                        style = LifeHubTypography.titleLarge,
                                        color = Colors.White,
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                IconButton(onClick = { onTodoClick(todoId) }) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_edit),
                                        contentDescription = "Edit",
                                        tint = Color.White
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            val completedItems = items.count { it.isComplete }
                            val totalItems = items.size
                            val progress =
                                if (totalItems > 0) completedItems.toFloat() / totalItems else 0f

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Completed $completedItems",
                                    style = LifeHubTypography.bodySmall,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                                Text(
                                    text = "Out of $totalItems",
                                    style = LifeHubTypography.bodySmall,
                                    color = Color.White.copy(alpha = 0.85f)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(Color.White.copy(alpha = 0.2f))
                            ) {
                                LinearProgressIndicator(
                                    progress = progress,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp),
                                    color = Colors.DarkPurple,
                                    backgroundColor = Color.Transparent
                                )
                            }
                        }
                    }
                }

                item {
                    TodoCard(data = items)
                }
            }
        }
    }
}

@Composable
private fun TodoCard(
    modifier: Modifier = Modifier,
    data: List<TodoItem>,
) {
    Card(
        modifier = modifier
            .baseHorizontalMargin()
            .padding(vertical = pd8),
        colors = CardDefaults.cardColors(
            containerColor = Colors.Black,
            contentColor = Colors.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(pd16)) {
            data.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = pd8),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = item.text,
                        modifier = Modifier.weight(1f),
                        style = LifeHubTypography.bodyLarge,
                        color = Colors.White
                    )

                    Text(
                        text = stringResource(
                            if (item.isComplete) R.string.complete else R.string.incomplete
                        ),
                        color = if (item.isComplete) Colors.SageGreen else Color.Red,
                        style = LifeHubTypography.titleSmall
                    )
                }
                Divider(color = Color.DarkGray.copy(alpha = 0.3f))
            }
        }
    }
}