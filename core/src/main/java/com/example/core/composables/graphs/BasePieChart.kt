package com.example.core.composables.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer

@Composable
fun BasePieChart(
    modifier: Modifier = Modifier,
    data: List<PieChartData.Slice>,
) {
    PieChart(
        modifier = modifier,
        pieChartData = PieChartData(data),
        animation = simpleChartAnimation(),
        sliceDrawer = SimpleSliceDrawer()
    )
}