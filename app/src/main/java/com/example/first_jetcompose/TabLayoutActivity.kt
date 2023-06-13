package com.example.first_jetcompose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.first_jetcompose.classes.TabRowItem
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class TabLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                Scaffold(topBar = { TopAppBar() },
                    content = {
                        Column(modifier = Modifier.padding(it)) {
                            TabLayout()
                        }
                    }) // Scaffold
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout() {
    val tabRowItems = listOf(
        TabRowItem("Tab 1",
            Icons.Rounded.Place,
            Color.White,
            screen = { TabScreen(text = "Place") }),
        TabRowItem("Tab 2",
            Icons.Rounded.Search,
            Color.Green,
            screen = { TabScreen(text = "Search") }),
        TabRowItem("Tab 3",
            Icons.Rounded.Star,
            Color.Magenta,
            screen = { TabScreen(text = "Star") }),
    )

    val pagerState = rememberPagerState() // remember and keep the state of the pager
    val coroutineScope = rememberCoroutineScope() // for pagerState scrolling

    Column {
        //ScrollableTabRow
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = MaterialTheme.colorScheme.onSecondary,
                    height = 5.dp
                )
            },
            backgroundColor = MaterialTheme.colorScheme.primary
        ) {
            tabRowItems.forEachIndexed { index, item ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = "",
                            tint = item.tint
                        )
                    },
                    text = {
                        Text(
                            text = item.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = item.tint
                        )
                    },
                )
            } // forEachIndexed
        } // TabRow

        HorizontalPager(
            count = tabRowItems.size,
            state = pagerState,
        ) {
            tabRowItems[pagerState.currentPage].screen()
        }
    } // Column
}

@Composable
fun TopAppBar() {
    val context = LocalContext.current
    TopAppBar(title = {
        Text(
            text = "Tab layout",
            fontSize = 18.sp
        )
    },
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        navigationIcon = {
            IconButton(onClick = { (context as Activity).finish() }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null,
                modifier = Modifier.size(32.dp))
            }
        }
    )
}

@Composable
fun TabScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun TabPreview() {
    TabLayout()
    TopAppBar()
}
