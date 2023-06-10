package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
