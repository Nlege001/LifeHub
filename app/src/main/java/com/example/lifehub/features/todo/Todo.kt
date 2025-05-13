package com.example.lifehub.features.todo

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd8
import com.example.lifehub.features.todo.data.TodoItem
import com.example.core.R as CoreR

@Composable
fun Todo(
    item: TodoItem,
    onTextChange: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = pd8)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = pd16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(pd8)
        ) {
            Icon(
                painter = painterResource(CoreR.drawable.ic_drag),
                contentDescription = stringResource(CoreR.string.drag),
                tint = Color.LightGray
            )

            val interactionSource = remember { MutableInteractionSource() }
            var isFocused by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (item.text.isEmpty()) {
                    Text(
                        text = "Enter a task...",
                        style = LifeHubTypography.labelLarge,
                        color = Color.White.copy(alpha = if (isFocused) 0.4f else 0.6f),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 4.dp)
                    )
                }

                BasicTextField(
                    value = item.text,
                    onValueChange = onTextChange,
                    modifier = Modifier
                        .onFocusChanged { isFocused = it.isFocused },
                    textStyle = LifeHubTypography.labelLarge.copy(
                        textDecoration = if (item.isComplete && item.text.isNotEmpty()) {
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        },
                        color = if (item.isComplete && item.text.isNotEmpty()) {
                            Color.LightGray
                        } else {
                            Color.White
                        }
                    ),
                    interactionSource = interactionSource
                )
            }

            Checkbox(
                checked = item.isComplete,
                onCheckedChange = { onCheckedChange(it) },
                colors = CheckboxDefaults.colors(
                    checkedColor = Colors.Lavender,
                    uncheckedColor = Color.LightGray,
                    disabledColor = Color.Gray
                ),
                enabled = item.text.isNotEmpty()
            )
        }
    }
}

@Preview(backgroundColor = 0xFF030303)
@Composable
fun PreviewTodoItem() {
    Todo(
        onCheckedChange = { },
        onTextChange = { },
        item = TodoItem(
            text = "I want to run the boston marathon",
            isComplete = true,
        )
    )
}