package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.DropdownMenuItem
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class DropDownActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DropDownMenu()
                }
            }
        }
    }
}

@Composable
fun DropDownMenu() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        // Dropdown 1
        var expanded by remember { mutableStateOf(false) }
        val items = listOf("A", "B", "C", "D", "E", "F", "G", "H")
        // val disableValue = "B"
        var selectedIndex by remember { mutableIntStateOf(0) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopCenter)
        ) {
            Text(
                text = items[selectedIndex],
                modifier = Modifier
                    .padding(10.dp)
                    .height(50.dp)
                    .fillMaxWidth()
                    .clickable(onClick = { expanded = !expanded })
                    .background(Color.Gray)
                    .padding(start = 8.dp),
                textAlign = TextAlign.Start,
                fontSize = 20.sp
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = !expanded },
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(250.dp)
                    .background(Color.Cyan)
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                    }) {
                        //val disabledText = if (s == disableValue) " (Disabled)" else ""
                        Text(text = s)// + disabledText)
                    }
                }
            }
        }

        // Dropdown 2
        var mExpanded by remember { mutableStateOf(false) }
        val mCities = listOf("Pune", "Ahmedabad", "Surat", "Vadodara", "Delhi", "Kolkata", "Mumbai")
        var mSelectedText by remember { mutableStateOf("") } // Store the selected city
        var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

        val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp
        else Icons.Filled.KeyboardArrowDown

        Column(
            modifier = Modifier
                .padding(20.dp)
                .padding(top = 50.dp)
        ) {
            OutlinedTextField(value = mSelectedText, onValueChange = { mSelectedText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates -> // To assign to the DropDown same width
                        mTextFieldSize = coordinates.size.toSize()
                    },
                label = { Text("Label") },
                trailingIcon = {
                    Icon(icon, null, Modifier.clickable { mExpanded = !mExpanded })
                }
            )

            DropdownMenu(
                expanded = mExpanded, onDismissRequest = { mExpanded = false },
                modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
            ) {
                mCities.forEach { label ->
                    DropdownMenuItem(onClick = { mSelectedText = label; mExpanded = false }) {
                        Text(label)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropPreview() {
    DropDownMenu()
}
