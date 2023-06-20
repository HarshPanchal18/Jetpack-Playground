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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                        modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ChartScreenLayout()
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

val colors = listOf(
    0xFFffd7d7,
    0xFFffe9d6,
    0xFFfffbd0,
    0xFFe3ffd9,
    0xFFd0fff8,
)

@Composable
fun ChartScreenLayout(list: List<DataPoint> = dataPoints) {
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    dataPoints = list, // List<DataPoint>
                    connection = LinePlot.Connection(Color(colors[0]), 2.dp), // Logic for the line between two adjacent
                    intersection = LinePlot.Intersection(Color(colors[1]), 5.dp),
                    highlight = LinePlot.Highlight(Color(colors[2]), 5.dp), // Highlighted when selected
                    areaUnderLine = LinePlot.AreaUnderLine(Color(colors[3]), 0.3f), // Area under the graph line
                )
            ),
        ),
        modifier = Modifier.fillMaxWidth().height(200.dp)
    )
}
