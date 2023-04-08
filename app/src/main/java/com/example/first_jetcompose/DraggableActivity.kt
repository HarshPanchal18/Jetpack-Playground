package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
class DraggableActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    DraggableScreen()
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun DraggableScreen() {
    Scaffold {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DraggableHorizontalBox()
            DraggableBox()
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun DraggableBox() {
    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Box(modifier = Modifier.offset { IntOffset(offsetX.roundToInt(),offsetY.roundToInt()) }
            .background(Color.Blue)
            .size(50.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
        )
    }
}

@Composable
fun DraggableHorizontalBox() {
    val max = 300.dp
    val min = 0.dp
    val (minPx,maxPx) = with(LocalDensity.current) { min.toPx() to max.toPx() }
    val offsetPosition = remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .width(max)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    val newValue = offsetPosition.value + delta
                    offsetPosition.value = newValue.coerceIn(minPx, maxPx)
                }
            )
            .background(MaterialTheme.colors.primary)
    ) {
        Box(
            Modifier
                .offset { IntOffset(offsetPosition.value.roundToInt(), 0) }
                .size(50.dp)
                .background(Color.Red)
        )
    }
}
