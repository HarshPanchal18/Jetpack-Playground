package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Android\nComing through the JetPack Compose ")
                }
                /*Column(
                    //modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconFloatingActionButton()
                }*/
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .size(300.dp)
    ) {
        Text(
            text = "Hello $name!",
            color = Color.Cyan,
            //modifier = Modifier.fillMaxSize()
        )
        Text(
            text = "Just started the Jetpack",
            color = Color.Green
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirstjetcomposeTheme {
        Greeting("Android")
    }
}

//@Preview
@Composable
private fun IconFloatingActionButton() {
    FloatingActionButton(onClick = {},
    shape = RectangleShape) {
        Icon(
            imageVector = Icons.Outlined.Star,
            contentDescription = null
        )
    }
}
