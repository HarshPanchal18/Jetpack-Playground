package com.example.first_jetcompose

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                /*Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Android\nComing through the JetPack Compose ")
                    Images()
                }*/
                Column(
                    //modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Greeting("Android\nComing through the JetPack Compose ")
                    Images()
                    IconFloatingActionButton()
                }
            //}
        }
    }
}

@Composable
fun Greeting(name: String) {
    Box(
        modifier = Modifier.size(300.dp)
    ) {
        Column {
            Text(
                text = "Hello $name!",
                color = Color.Cyan,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Just started the Jetpack",
                color = Color.Green
            )
        }
    }
}

@Composable
fun Images() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.background(Color.LightGray),
        )
        Icon( // for the vectors
            imageVector = Icons.Default.AccountBox,
            contentDescription = null,
            modifier = Modifier
                .background(Color.Blue)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirstjetcomposeTheme {
        Greeting("Android")
        Images()
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
