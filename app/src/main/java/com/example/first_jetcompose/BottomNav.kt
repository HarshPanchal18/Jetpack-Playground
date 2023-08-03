package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.classes.TabRowItem
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

// https://stackoverflow.com/questions/76816605/how-to-reduce-top-and-bottom-padding-of-material3-navigationbar#comment135431315_76816605
class BottomNavi : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.padding(10.dp),
                        bottomBar = {
                            Previewscreens()
                        }
                    ) { it }
                }
            }
        }
    }
}

@Composable
private fun TabScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
fun Previewscreens() {
    val tabs = listOf(
        TabRowItem("Tab 1",
            Icons.Rounded.Place,
            Color.White,
            screen = { TabScreen(text = "Place") }),
        TabRowItem("Tab 2",
            Icons.Rounded.Search,
            Color.Green,
            screen = { TabScreen(text = "Search") }),
        /*TabRowItem("Tab 3",
            Icons.Rounded.Star,
            Color.Magenta,
            screen = { TabScreen(text = "Star") }),*/
    )
    NavigationBar(
        //modifier = Modifier.height(35.dp)
    ) {
        tabs.forEach { tab ->
            NavigationBarItem(
                label = { Text(tab.title) },
                icon = { Icon(tab.icon, null) },
                onClick = {},
                selected = true,
                modifier = Modifier
                    .background(Color.Yellow)
                //.padding(10.dp)
            )
        }
    }
}
