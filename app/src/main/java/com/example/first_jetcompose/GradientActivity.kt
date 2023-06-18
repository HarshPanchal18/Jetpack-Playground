package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class GradientActivity : ComponentActivity() {
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
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LinearGradient()
                        HorizontalGradient()
                        VerticalGradient()
                        RadialGradient()
                    }
                }
            }
        }
    }
}

@Composable
fun LinearGradient() {
    val gradient = Brush.linearGradient(
        0.0f to Color.Magenta,
        500.0f to Color.Cyan,
        start = Offset.Zero,
        end = Offset.Infinite,
    )
    Box(
        modifier = Modifier
            .background(gradient)
            .size(150.dp)
    )
}

@Composable
fun HorizontalGradient() {
    val gradient = Brush.horizontalGradient(
        0.0f to Color.Magenta,
        1.0f to Color.Cyan,
        startX = 0.0f,
        endX = 1000.0f
    )
    Box(
        modifier = Modifier
            .background(gradient)
            .size(150.dp)
    )
}

@Composable
fun VerticalGradient() {
    val gradient = Brush.verticalGradient(
        0.0f to Color.Magenta,
        1.0f to Color.Cyan,
        startY = 0.0f,
        endY = 1500.0f
    )
    Box(
        modifier = Modifier
            .background(gradient)
            .size(150.dp)
    )
}

@Composable
fun RadialGradient() {
    val gradient = Brush.radialGradient(
        0.0f to Color.Magenta,
        1.0f to Color.Cyan,
        radius = 300.0f,
        tileMode = TileMode.Repeated
    )
    Box(
        modifier = Modifier
            .background(gradient)
            .size(150.dp)
    )
}

@Preview
@Composable
fun GreetingPreview() {
    Column {
        LinearGradient()
        HorizontalGradient()
        VerticalGradient()
        RadialGradient()
    }
}
