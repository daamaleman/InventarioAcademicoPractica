package ni.edu.uam.inventarioacademicopractica.ui.component

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun BarChartComponent(
    data: Map<String, Int>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp),
        factory = { context ->
            BarChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = false
                setPinchZoom(false)
                setDrawGridBackground(false)
                setDrawBarShadow(false)
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    granularity = 1f
                }
                axisLeft.setDrawGridLines(false)
                axisRight.isEnabled = false
            }
        },
        update = { chart ->
            val entries = data.values.mapIndexed { index, value ->
                BarEntry(index.toFloat(), value.toFloat())
            }
            
            val labels = data.keys.toList()
            
            val dataSet = BarDataSet(entries, "Equipos por Categoría").apply {
                color = Color.BLUE
                valueTextSize = 12f
            }
            
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            chart.data = BarData(dataSet)
            chart.invalidate()
        }
    )
}
