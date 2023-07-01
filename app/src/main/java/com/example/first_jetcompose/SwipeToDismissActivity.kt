@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class SwipeToDismissActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DismissScreen()
                }
            }
        }
    }
}

val dessertList = listOf("Cupcake", "Choco Lava Cake", "Sugar candy")

@Composable
fun DismissScreen(list: List<String> = dessertList) {
    val dismissState = rememberDismissState()
    /*Column {
        list.forEach { element ->
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(
                    DismissDirection.StartToEnd,
                    DismissDirection.EndToStart
                ),
                background = { SwipeToDismissBackground(state = dismissState) },
                dismissContent = {
                    Column {
                        Card(modifier = Modifier.padding(5.dp)) {
                                ListItem(
                                    headlineContent = { Text(element) },
                                    supportingContent = { Text("Swipe me left or right!") }
                                )
                                Divider()
                        } // Card
                    }
                }
            )
        }
    }*/
    Column {
        list.forEach { element ->
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(
                    DismissDirection.StartToEnd,
                    DismissDirection.EndToStart
                ),
                background = { SwipeToDismissBackground(state = dismissState) },
                dismissContent = {
                    Card(modifier = Modifier.padding(5.dp)) {
                        Column {
                            ListItem(
                                headlineContent = { Text(element) },
                                supportingContent = { Text("Swipe me left or right!") }
                            )
                            Divider()
                        }
                    }
                }
            )
        }
    }

}

@Composable
fun SwipeToDismissBackground(state: DismissState) {
    val color by animateColorAsState(
        when (state.targetValue) {
            DismissValue.Default -> Color.LightGray
            DismissValue.DismissedToEnd -> Color.Green.copy(0.5f)
            DismissValue.DismissedToStart -> Color.Red.copy(0.5f)
        }
    )
    Box(
        Modifier
            .fillMaxSize()
            .background(color)
    )
}
