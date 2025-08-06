package com.example.core.composables.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation

@Composable
fun BaseBarChart(
    modifier: Modifier = Modifier,
    data: List<BarChartData.Bar>,
) {
    BarChart(
        modifier = modifier,
        animation = simpleChartAnimation(),
        xAxisDrawer = SimpleXAxisDrawer(),
        yAxisDrawer = SimpleYAxisDrawer(),
        barChartData = BarChartData(data)
    )
}