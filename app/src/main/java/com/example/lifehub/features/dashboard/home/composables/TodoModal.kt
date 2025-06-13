package com.example.lifehub.features.dashboard.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieConstants
import com.example.core.analytics.Page
import com.example.core.composables.LottieWrapper
import com.example.core.composables.ViewStateCoordinator
import com.example.core.theme.LifeHubTypography
import com.example.core.utils.baseHorizontalMargin
import com.example.core.utils.toReadableDate
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd16
import com.example.core.values.Dimens.pd20
import com.example.core.values.Dimens.pd8
import com.example.lifehub.features.dashboard.home.viewmodel.TodoModalViewModel
import com.example.lifehub.features.todo.data.TodoData
import com.example.wpinterviewpractice.R

@Composable
fun TodoModal(
    viewModel: TodoModalViewModel = hiltViewModel()
) {
    Card(
        modifier = Modifier
            .padding(pd16)
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(pd20),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(pd8),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Colors.DarkPurple,
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            ViewStateCoordinator(
                state = viewModel.data,
                refresh = { viewModel.getData() },
                page = Page.DASHBOARD_HOME,
            ) {
                Content(
                    data = it
                )
            }
        }
    }
}

@Composable
private fun Content(
    data: List<TodoData>
) {
    Column(modifier = Modifier.baseHorizontalMargin(pd16)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.todo),
                style = LifeHubTypography.titleLarge,
                color = Color.White
            )

            LottieWrapper(
                modifier = Modifier.height(70.dp),
                file = R.raw.anim_todo,
                iterations = LottieConstants.IterateForever
            )
        }

        val dataToRender = data.take(1)

        if (dataToRender.isEmpty()) {
            Text(
                text = stringResource(R.string.empty_todo),
                style = LifeHubTypography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            dataToRender.forEach {
                it.date?.let { date ->
                    Text(
                        text = date.toReadableDate(),
                        style = LifeHubTypography.bodySmall,
                        color = Color.White
                    )
                }

                it.items.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = item.isComplete,
                            onCheckedChange = {}
                        )

                        Text(
                            text = item.text,
                            modifier = Modifier.weight(1f),
                            style = LifeHubTypography.bodyLarge,
                            color = Colors.White
                        )
                    }
                }
            }
        }
    }
}