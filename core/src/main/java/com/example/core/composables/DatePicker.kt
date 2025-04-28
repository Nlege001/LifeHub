package com.example.core.composables

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.theme.LifeHubTypography
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd8
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DatePicker(
    selectedDate: Long?,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Date of Birth"
) {
    var expanded by remember { mutableStateOf(false) }
    var textValue by remember {
        mutableStateOf(
            TextFieldValue(selectedDate?.let { convertMillisToDate(it) } ?: "")
        )
    }
    var validationError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Column(modifier = modifier) {
        OutlinedTextField(
            value = textValue,
            onValueChange = {
                textValue = it
                parseDateString(it.text)?.let { millis ->
                    val error = validateDate(millis, context)
                    validationError = error
                    if (error == null) {
                        onDateSelected(millis)
                    }
                }
            },
            label = {
                Text(
                    text = label,
                    style = LifeHubTypography.bodyMedium,
                    color = Colors.White
                )
            },
            placeholder = { Text(stringResource(R.string.date_format_place_holder)) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.selected_date),
                        tint = Colors.White
                    )
                }
            },
            readOnly = false,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Colors.White,
                focusedBorderColor = Colors.Indigo,
                unfocusedBorderColor = Colors.White
            ),
            isError = validationError != null
        )

        validationError?.let {
            Text(
                modifier = Modifier.padding(vertical = pd8),
                text = it,
                style = LifeHubTypography.bodySmall,
                color = Color.Red
            )
        }

        if (expanded) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(MaterialTheme.colors.surface)
            ) {
                DatePickerDropdown(
                    initialDateMillis = selectedDate,
                    onDateSelected = {
                        textValue = TextFieldValue(convertMillisToDate(it))
                        val error = validateDate(it, context)
                        validationError = error
                        if (error == null) {
                            onDateSelected(it)
                        }
                        expanded = false
                    },
                    onDismiss = { expanded = false }
                )
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@SuppressLint("SimpleDateFormat")
private fun parseDateString(dateString: String): Long? {
    return try {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        formatter.parse(dateString)?.time
    } catch (e: Exception) {
        null
    }
}

private fun validateDate(
    millis: Long,
    context: Context
): String? {
    val now = System.currentTimeMillis()

    if (millis > now) {
        return context.getString(R.string.dob_future_error)
    }

    val ageInMillis = now - millis
    val millisIn18Years = 18L * 365 * 24 * 60 * 60 * 1000

    if (ageInMillis < millisIn18Years) {
        return context.getString(R.string.dob_under_18)
    }

    return null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDropdown(
    initialDateMillis: Long?,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDateMillis)

    Column(
        modifier = Modifier
            .background(Colors.Black)
            .padding(16.dp)
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.cancel))
            }
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(it)
                    }
                }
            ) {
                Text(stringResource(R.string.ok))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewDatePickerField() {
    DatePicker(
        selectedDate = null,
        onDateSelected = {}
    )
}
