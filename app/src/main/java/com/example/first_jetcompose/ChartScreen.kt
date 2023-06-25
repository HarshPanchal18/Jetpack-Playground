package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot

class ChartScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ChartScreenLayout()
                        Text("(Line chart)")

                        PieChart()
                        Text("(Pie chart)")
                    }
                }
            }
        }
    }
}

private val dataPoints = listOf(
    DataPoint(0f, 0f),
    DataPoint(1f, 20f),
    DataPoint(2f, 46f),
    DataPoint(3f, -31f),
    DataPoint(4f, 155f),
    DataPoint(5f, 42f),
    DataPoint(6f, 75f),
    DataPoint(7f, -69f),
    DataPoint(8f, 30f),
    DataPoint(9f, 90f),
    DataPoint(10f, 13f),
    DataPoint(11f, 37f),
    DataPoint(12f, 85f),
    DataPoint(13f, 2f),
    DataPoint(14f, 0f),
    DataPoint(15f, -44f),
    DataPoint(16f, 71f),
    DataPoint(17f, 7f),
    DataPoint(18f, 43f),
    DataPoint(19f, 120f),
    DataPoint(20f, 37f),
)

@Composable
fun ChartScreenLayout(list: List<DataPoint> = dataPoints) {
    val colors = listOf(
        0xFFffd7d7,
        0xFFffe9d6,
        0xFFfffbd0,
        0xFFe3ffd9,
        0xFFd0fff8,
    )

    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    dataPoints = list, // List<DataPoint>
                    connection = LinePlot.Connection(
                        color = Color(colors[0]),
                        strokeWidth = 2.dp
                    ), // Logic for the line between two adjacent
                    intersection = LinePlot.Intersection(Color(colors[1]), 5.dp),
                    highlight = LinePlot.Highlight(
                        color = Color(colors[2]),
                        radius = 5.dp
                    ), // Highlighted when selected
                    areaUnderLine = LinePlot.AreaUnderLine(
                        color = Color(colors[3]),
                        alpha = 0.3f
                    ), // Area under the graph line
                )
            ),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun PieChart(
    values: List<Float> = listOf(15f, 30f, 25f),
    colors: List<Color> = listOf(Color(0xFF58BDFF), Color(0xFF125B7F), Color(0xFF092D40)),
    legend: List<String> = listOf("Mango", "Banana", "Apple"),
    size: Dp = 200.dp
) {
    val sumOfValues = values.sum()
    val proportions = values.map { it * 100 / sumOfValues } // Calculate each proportion value
    val sweepAngles = proportions.map { 360 * it / 100 } // Convert proportion to angle

    Canvas(modifier = Modifier.size(size = size)) {
        var startAngle = -90f
        for (i in sweepAngles.indices) {
            drawArc(
                color = colors[i],
                startAngle = startAngle,
                sweepAngle = sweepAngles[i],
                useCenter = true
            )
            startAngle += sweepAngles[i]
        }
    }
    //Spacer(Modifier.height(32.dp))
    Column {
        for (i in values.indices) {
            DisplayLegend(color = colors[i], legend = legend[i])
        }
    }
}

@Composable
fun DisplayLegend(color: Color, legend: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier.width(16.dp),
            thickness = 4.dp,
            color = color
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = legend,
            modifier = Modifier.padding(start = 10.dp),
            color = Color.Black
        )
    }
}
