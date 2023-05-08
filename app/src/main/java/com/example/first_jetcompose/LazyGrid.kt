package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class LazyGrid : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    LazyGridScreen()
                }
            }
        }
    }
}

@Composable
@Preview
private fun LazyGridScreen() {
    Scaffold { it
        val itemsList = (1..250).toList()
        LazyVerticalGrid(//cells = GridCells.Adaptive(70.dp),
            columns = GridCells.Adaptive(70.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            items(itemsList) { it1 ->
                Text(
                    textAlign = TextAlign.Center,
                    text = it1.toString(),
                    modifier = Modifier
                        .padding(5.dp)
                        .height(40.dp)
                        .background(MaterialTheme.colors.primary)
                )
            }
        }
    }
}
