package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
private fun LazyGridScreen() {
    Scaffold {
        val itemsList = (1..250).toList()
        LazyVerticalGrid(cells = GridCells.Adaptive(70.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            items(itemsList) {
                Text(
                    textAlign = TextAlign.Center,
                    text = it.toString(),
                    modifier = Modifier
                        .padding(5.dp)
                        .height(40.dp)
                        .background(MaterialTheme.colors.primary)
                )
            }
        }
    }
}

@Composable
@Preview
private fun Circle() {
    Scaffold {
        Box {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(180.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            )
        }
    }
}
