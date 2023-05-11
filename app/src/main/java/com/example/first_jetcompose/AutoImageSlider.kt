package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.delay

class AutoImageSlider : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SliderScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun SliderScreen() {
    val images = listOf(
        "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
        "https://cdn.pixabay.com/photo/2023/05/03/16/11/alfa-romeo-7968027_960_720.jpg",
        "https://cdn.pixabay.com/photo/2015/05/28/23/12/auto-788747__340.jpg",
        "https://cdn.pixabay.com/photo/2016/11/22/20/10/dog-1850465__340.jpg",
        "https://cdn.pixabay.com/photo/2016/11/29/09/32/auto-1868726__340.jpg",
        "https://cdn.pixabay.com/photo/2017/03/27/14/56/auto-2179220__340.jpg"
    )
    Card(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        AutoSlidingCarousel(
            itemsCount = images.size,
            itemContent = { index ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(images[index]).build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.height(200.dp)
                )
            }
        )
    }
}

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

//@Preview(showBackground = true)
@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color = Color.Yellow,
    unSelectedColor: Color = Color.Gray,
    dotSize: Dp,
) { //a list of dots with padding between them…
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            IndicatorDot(
                size = dotSize,
                color = if (index == selectedIndex) selectedColor else unSelectedColor
            )
            if (index != totalDots - 1)
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 2000,
    pagerState: PagerState = remember { PagerState() },
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState() // for setting the selected index of the indicator accurately.
    LaunchedEffect(pagerState.currentPage) {
        /*The LaunchedEffect will be triggered both when the AutoSlidingCarousel is first composed
        and whenever the currentPage value changes.
        This ensures that the delay is reset if the user manually scrolls the carousel.*/
        delay(autoSlideDuration)
        pagerState.animateScrollToPage((pagerState.currentPage + 1) % itemsCount)
    }

    Box(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            itemContent(page)
        }

        Surface(
            modifier = modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color.Black.copy(0.5f)
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
        }
    }
}
