package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class BorderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    BorderScreen()
                }
            }
        }
    }
}

@Composable
fun BorderScreen() {
    Scaffold {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Border()
            BorderSolidBrush()
            BorderLinearGradientBrush()
            BorderRadialGradientBrush()
            BorderSweepGradientBrush()
        }
    }
}

@Composable
fun Border() {
    Card(border = BorderStroke(2.dp, Color.Black)) {
        DefaultText()
    }
}

@Composable
fun DefaultText() {
    Text(text = stringResource(id = R.string.app_name),
        modifier = Modifier.padding(12.dp))
}

@Preview
@Composable
private fun BorderSolidBrush() {
    Card(border = BorderStroke(2.dp, SolidColor(Color.Red))) {
        DefaultText()
    }
}

@Preview
@Composable
private fun BorderLinearGradientBrush() {
    Card(border = BorderStroke(2.dp, Companion.linearGradient(listOf(Color.Red,Color.Blue,Color.Cyan)))) {
        DefaultText()
    }
}

@Preview
@Composable
private fun BorderRadialGradientBrush() {
    Card(border = BorderStroke(2.dp, Companion.radialGradient(listOf(Color.Red,Color.Blue,Color.Cyan)))) {
        DefaultText()
    }
}

@Preview
@Composable
private fun BorderSweepGradientBrush() {
    Card(border = BorderStroke(2.dp, Companion.sweepGradient(listOf(Color.Red,Color.Blue,Color.Cyan)))) {
        DefaultText()
    }
}
