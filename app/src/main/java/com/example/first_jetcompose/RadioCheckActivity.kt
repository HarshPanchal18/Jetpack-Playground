package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Checkbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class RadioCheckActivity : ComponentActivity() {
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
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        RadioButtonScreen()
                        TriCheckBoxScreen()
                        ChipsRow()
                        SliderDemo()
                        SteppedSliderDemo()
                    }
                }
            }
        }
    }
}

@Composable
fun RadioButtonScreen() {
    val data = listOf("Male", "Female", "Other", "Prefer not to say")
    var radioState by remember { mutableStateOf("Male") }

    Box(
        modifier = Modifier.padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            data.forEach {
                RadioButtonRow(selected = radioState == it, text = it) { data -> radioState = data }
            }
            Text(text = radioState)
        }
    }
}

@Composable
fun RadioButtonRow(
    selected: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    onValueUpdate: (String) -> Unit
) {
    Row {
        RadioButton(selected = selected, onClick = { onValueUpdate(text) })
        Text(text = text, modifier = modifier.align(CenterVertically))
    }
}

@Composable
fun TriCheckBoxScreen() {
    var pizzaCheckerState by remember { mutableStateOf(false) }
    var pastaCheckerState by remember { mutableStateOf(false) }
    var cakeCheckerState by remember { mutableStateOf(false) }

    val foodCheckBoxState by remember(  // remember by both the keys otherwise new value by calculations
        pizzaCheckerState,
        pastaCheckerState,
        cakeCheckerState
    ) {
        derivedStateOf {
            if (pizzaCheckerState && pastaCheckerState && cakeCheckerState) ToggleableState.On // Toggle Check ON if all checked
            else if (!pizzaCheckerState && !pastaCheckerState && !cakeCheckerState) ToggleableState.Off // Toggle Check OFF if all unchecked
            else ToggleableState.Indeterminate // Toggle Check Dash on single unchecked
        }
    }

    Column {
        Row(verticalAlignment = CenterVertically) {
            TriStateCheckbox(state = foodCheckBoxState, onClick = {
                if (pizzaCheckerState && pastaCheckerState) {
                    pizzaCheckerState = false
                    pastaCheckerState = false
                    cakeCheckerState = false
                } else {
                    pizzaCheckerState = true
                    pastaCheckerState = true
                    cakeCheckerState = true
                }
            })
            Text(text = "Food")
        }

        CheckBoxItem(checked = pizzaCheckerState, dish = "Pizza")
        { pizzaCheckerState = !pizzaCheckerState }

        CheckBoxItem(checked = pastaCheckerState, dish = "Pasta")
        { pastaCheckerState = !pastaCheckerState }

        CheckBoxItem(checked = cakeCheckerState, dish = "Cake")
        { cakeCheckerState = !cakeCheckerState }
    }
}

// For sub-checks
@Composable
fun CheckBoxItem(
    checked: Boolean,
    dish: String,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.padding(start = 15.dp),
        verticalAlignment = CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = { checked_ -> onCheckedChange(checked_) })
        Text(
            text = dish,
            modifier = modifier.padding(start = 2.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipsRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        AssistChip(
            onClick = {},
            label = { Text("Assist Chip") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Localized description",
                    Modifier.size(AssistChipDefaults.IconSize)
                )
            },
            modifier = Modifier.padding(start = 8.dp),
        )

        var selected by remember { mutableStateOf(false) }
        FilterChip(
            selected = selected,
            onClick = { selected = !selected },
            label = { Text("Filter chip") },
            leadingIcon = {
                if (selected) Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Localized Description",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
                else Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Localized Description",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            },
            modifier = Modifier.padding(start = 8.dp)
        )

        InputChip(
            selected = selected,
            onClick = { selected = !selected },
            label = { Text("Input chip") },
            avatar = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier.size(InputChipDefaults.AvatarSize)
                )
            },
            modifier = Modifier.padding(start = 8.dp)
        )

        SuggestionChip(
            onClick = {},
            label = { Text("Suggestion Chip") },
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun SliderDemo() {
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = (sliderPosition * 100).toString())
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            modifier = Modifier.padding(horizontal = 10.dp),
        )
    }
}

@Composable
fun SteppedSliderDemo() {
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = (sliderPosition * 100).toString())
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            modifier = Modifier.padding(horizontal = 10.dp),
            steps = 4
        )
    }
}
