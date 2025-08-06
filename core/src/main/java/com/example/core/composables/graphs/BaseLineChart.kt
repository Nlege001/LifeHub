package com.example.core.composables.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation

@Composable
fun BaseLineChart(
    modifier: Modifier = Modifier,
    data: List<LineChartData>
) {
    LineChart(
        modifier = modifier,
        animation = simpleChartAnimation(),
        pointDrawer = FilledCircularPointDrawer(),
        linesChartData = data
    )
}