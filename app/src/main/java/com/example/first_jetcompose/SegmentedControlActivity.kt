package com.example.first_jetcompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.compose.material3.TopAppBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class SegmentedControlActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) { SegmentedControlSwitchPage() }
            }
        }
    }
}

@SuppressLint("AutoboxingStateCreation")
@Composable
fun SegmentedControlSwitch(
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 120.dp,
    cornerRadius: Int = 10,
    @ColorRes color: Int = R.color.teal_200,
    onItemSelection: (selectedItemIndex: Int) -> Unit
) {
    val selectedIndex = remember { mutableStateOf(defaultSelectedItemIndex) }

    Row {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = when (index) {
                    0 -> {
                        if (useFixedWidth)
                            Modifier
                                .width(itemWidth)
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedIndex.value == 0) 1f else 0f)
                        else
                            Modifier
                                .wrapContentSize()
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedIndex.value == 0) 1f else 0f)
                    }

                    else -> {
                        if (useFixedWidth)
                            Modifier
                                .width(itemWidth)
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedIndex.value == index) 1f else 0f)
                        else
                            Modifier
                                .wrapContentSize()
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedIndex.value == index) 1f else 0f)
                    }
                },
                onClick = {
                    selectedIndex.value = index
                    onItemSelection(selectedIndex.value)
                },
                shape = when (index) {
                    // Left Outer Button
                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = 0,
                        bottomEndPercent = 0,
                        bottomStartPercent = cornerRadius,
                    )
                    // Right Outer Button
                    items.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = 0,
                        bottomEndPercent = cornerRadius
                    )
                    // Middle Button
                    else -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 0,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                },
                border = BorderStroke(
                    width = 1.dp,
                    color = if (selectedIndex.value == index) colorResource(color)
                    else colorResource(color).copy(0.75F)
                ),
                colors =
                if (selectedIndex.value == index)
                    ButtonDefaults.outlinedButtonColors(containerColor = colorResource(color))
                else ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = item,
                    fontWeight = FontWeight.Normal,
                    color =
                    if (selectedIndex.value == index) Color.White
                    else colorResource(color).copy(0.9F)
                )
            } // OutlinedButton
        }
    } // Row
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedControlSwitchPage() {
    val genders = listOf("Male", "Female")
    val context = LocalContext.current
    val expanded = remember { mutableStateOf(false) }
    val menuItems = listOf("Cancel", "Delete", "View")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Segment Switches") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Green.copy(0.5F)
                ),
                actions = {
                    IconButton(onClick = { expanded.value = true }) {
                        Icon(Icons.Default.MoreVert, null, tint = Color.White)
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                        offset = DpOffset((-40).dp, (-40).dp),
                        modifier = Modifier.background(Color.White)
                    ) {
                        menuItems.forEach {
                            DropdownMenuItem(onClick = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                expanded.value = !expanded.value
                            }) { Text(it) }
                        }
                    } // DropDownMenu
                }, // actions
            )
        },
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp)
                    .background(color = Color.White)
            ) {
                // Wrap Size
                Text("Wrap size:")
                Spacer(Modifier.height(10.dp))
                SegmentedControlSwitch(
                    items = genders,
                    defaultSelectedItemIndex = 0
                ) {}

                Spacer(Modifier.height(10.dp))
                SegmentedControlSwitch(
                    items = genders,
                    cornerRadius = 50,
                    color = R.color.purple_200
                ) {}
                Spacer(Modifier.height(30.dp))

                // Fixed size
                Text("Fixed size")
                Spacer(Modifier.height(10.dp))
                SegmentedControlSwitch(
                    items = genders,
                    useFixedWidth = true,
                    itemWidth = 120.dp
                ) {}

                Spacer(Modifier.height(10.dp))
                SegmentedControlSwitch(
                    items = genders,
                    useFixedWidth = true,
                    itemWidth = 120.dp,
                    cornerRadius = 50,
                    color = R.color.purple_200
                ) {}
                Spacer(Modifier.height(30.dp))

                // Multiple Items
                Text(text = "Multiple items : ")
                Spacer(modifier = Modifier.height(10.dp))
                val items = listOf("One", "Two", "Three", "Four")
                SegmentedControlSwitch(items = items) {}

                Spacer(modifier = Modifier.height(10.dp))
                SegmentedControlSwitch(
                    items = items,
                    color = R.color.purple_200,
                    cornerRadius = 50
                ) {}
            } // Column
        } // Box
    } // Scaffold
}
