package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
