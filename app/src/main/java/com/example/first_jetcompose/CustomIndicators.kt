package com.example.first_jetcompose

import android.os.Bundle
import com.google.accompanist.pager.HorizontalPager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

class CustomIndicators : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column { PagerScreen() }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerScreen() {
    val pageCount = 3
    val pagerState = rememberPagerState()

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(count = pageCount, state = pagerState, modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) { Text(text = "Page: ${it + 1}", fontSize = 30.sp) }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        ) {
            repeat(pageCount) { CustomIndicator(selected = pagerState.currentPage == it) }
        }
    }
}

@Composable
fun CustomIndicator(
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = 2.dp)
            .background(
                color = if (selected) Color.Red else Color.Gray,
                shape = CircleShape
            )
            .size(10.dp)
    )
}
