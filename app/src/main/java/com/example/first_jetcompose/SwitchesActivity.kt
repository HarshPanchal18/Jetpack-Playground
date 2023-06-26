package com.example.first_jetcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class SwitchesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Switch3Screen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Switch3Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultSwitch()
        DisabledSwitch()
        CheckedThumbColorSwitch()
        CheckedTrackColorSwitch()
        UncheckedThumbColorSwitch()
        UncheckedTrackColorSwitch()
        DisableCheckedThumbColorSwitch()
        DisableCheckedTrackColorSwitch()
        DisableUncheckedThumbColorSwitch()
        DisableUncheckedTrackColorSwitch()
        CustomSwitch()
    }
}

@Composable
fun DefaultSwitch() {
    val isChecked = remember { mutableStateOf(true) }
    Switch(
        modifier = Modifier.padding(16.dp),
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = it }
    )
}

@Composable
fun DisabledSwitch() {
    val isChecked = remember { mutableStateOf(false) }
    Switch(
        modifier = Modifier.padding(16.dp),
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = it },
        enabled = false
    )
}

@Composable
fun CheckedThumbColorSwitch() {
    val isChecked = remember { mutableStateOf(true) }
    Switch(
        modifier = Modifier.padding(16.dp),
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = it },
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.Red
        )
    )
}

@Composable
fun CheckedTrackColorSwitch() {
    val isChecked = remember { mutableStateOf(true) }
    Switch(
        modifier = Modifier.padding(16.dp),
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = it },
        colors = SwitchDefaults.colors(
            checkedTrackColor = Color.Red
        )
    )
}

@Composable
fun UncheckedThumbColorSwitch() {
    val isChecked = remember { mutableStateOf(true) }
    Switch(
        modifier = Modifier.padding(16.dp),
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = it },
        colors = SwitchDefaults.colors(
            uncheckedThumbColor = Color.Gray
        )
    )
}

@Composable
fun UncheckedTrackColorSwitch() {
    val isChecked = remember { mutableStateOf(true) }
    Switch(
        modifier = Modifier.padding(16.dp),
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = it },
        colors = SwitchDefaults.colors(
            uncheckedTrackColor = Color.LightGray
        )
    )
}

@Composable
fun DisableCheckedThumbColorSwitch() {
    val isChecked = remember { mutableStateOf(true) }
    Switch(
        modifier = Modifier.padding(16.dp),
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = it },
        colors = SwitchDefaults.colors(
            disabledCheckedThumbColor = Color.Gray
        )
    )
}

@Composable
fun DisableCheckedTrackColorSwitch() {
    val isChecked = remember { mutableStateOf(true) }
    Switch(
        modifier = Modifier.padding(16.dp),
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = it },
        colors = SwitchDefaults.colors(
            disabledCheckedTrackColor = Color.Gray
        )
    )
}

@Composable
fun DisableUncheckedThumbColorSwitch() {
    val isChecked = remember { mutableStateOf(false) }
    Switch(
        modifier = Modifier.padding(16.dp),
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = it },
        colors = SwitchDefaults.colors(
            disabledUncheckedThumbColor = Color.Gray
        )
    )
}

@Composable
fun DisableUncheckedTrackColorSwitch() {
    val isChecked = remember { mutableStateOf(false) }
    Switch(
        modifier = Modifier.padding(16.dp),
        checked = isChecked.value,
        onCheckedChange = { isChecked.value = it },
        colors = SwitchDefaults.colors(
            disabledUncheckedTrackColor = Color.LightGray
        )
    )
}

@Composable
fun CustomSwitch(
    width: Dp = 72.dp,
    height: Dp = 40.dp,
    checkedTrackColor: Color = Color(0xFF35898F),
    uncheckedTrackColor: Color = Color(0xFFe0e0e0),
    gapBetweenThumbAndTrackEdge: Dp = 8.dp,
    borderWidth: Dp = 4.dp,
    cornerSize: Int = 50,
    iconInnerPadding: Dp = 4.dp,
    thumbSize: Dp = 24.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    var switchOn by remember { mutableStateOf(true) }
    val alignment by animateAlignmentAsState(if (switchOn) 1f else -1f) // For moving the thumb

    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .border(
                width = borderWidth,
                color = if (switchOn) checkedTrackColor else uncheckedTrackColor,
                shape = RoundedCornerShape(percent = cornerSize)
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                switchOn = !switchOn
            },
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .padding(horizontal = gapBetweenThumbAndTrackEdge)
                .fillMaxSize(),
            contentAlignment = alignment
        ) {
            Icon(
                imageVector = if (switchOn) Icons.Filled.Done else Icons.Filled.Close,
                contentDescription = if (switchOn) "Enabled" else "Disabled",
                modifier = Modifier
                    .size(thumbSize)
                    .background(
                        color = if (switchOn) checkedTrackColor else uncheckedTrackColor,
                        shape = CircleShape
                    )
                    .padding(iconInnerPadding),
                tint = Color.White
            )
        }
    }
    // gap between switch and the text
    Spacer(modifier = Modifier.height(10.dp))
    Text(text = if (switchOn) "ON" else "OFF")
    Spacer(modifier = Modifier.height(16.dp))
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun animateAlignmentAsState(targetBiasValue: Float): State<BiasAlignment> {
    // Method 0
    val bias by animateFloatAsState(targetValue = targetBiasValue)
    return derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) }

    // Method 1
    /*val bias = remember { mutableFloatStateOf(0f) }
    val derivedBias = derivedStateOf { BiasAlignment(horizontalBias = bias.value, verticalBias = 0f) }
    LaunchedEffect(targetBiasValue) { bias.value = targetBiasValue }
    return derivedBias*/
}
