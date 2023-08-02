package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.accompanist.pager.HorizontalPager
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
// https://github.com/ryanwong-uk/compose-pager-demo
import kotlin.math.absoluteValue

class HorizontalPagerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PagerMainScreen(modifier = Modifier.fillMaxSize())
        }
    }

    @Preview(
        showSystemUi = true,
        showBackground = true
    )
    @Composable
    private fun PagerMainScreen(modifier: Modifier = Modifier) {
        val screenWidth = LocalConfiguration.current.screenWidthDp
        val pageWidth = (screenWidth / 3).dp
        val drawables = listOf(
            R.drawable.dog1,
            R.drawable.dog2,
            R.drawable.dog3,
            R.drawable.dog1,
            R.drawable.dog2,
            R.drawable.dog3,
        )

        FirstjetcomposeTheme {
            Surface(
                modifier = modifier,
                color = MaterialTheme.colorScheme.background,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    AnimatedViewPager(
                        modifier = Modifier.fillMaxWidth(),
                        pageSize = pageWidth,
                        drawables = drawables
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun AnimatedViewPager(modifier: Modifier, pageSize: Dp, drawables: List<Int>) {
        val pagerState = rememberPagerState(initialPage = 0)

        HorizontalPager(
            modifier = modifier,
            count = drawables.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = pageSize),
            verticalAlignment = Alignment.CenterVertically
        ) { thisPageIndex ->
            PageLayout(
                modifier = Modifier
                    .size(size = pageSize)
                    .pagerAnimation(
                        pagerState = pagerState,
                        thisPageIndex = thisPageIndex
                    ),
                drawable = drawables[thisPageIndex]
            )
        }
    }

    @Composable
    private fun PageLayout(
        modifier: Modifier = Modifier,
        @DrawableRes drawable: Int
    ) {
        Card(modifier = modifier) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = drawable),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
private fun Modifier.pagerAnimation(pagerState: PagerState, thisPageIndex: Int) = then(
    graphicsLayer {
        val pageOffset = (
                (pagerState.currentPage - thisPageIndex) + pagerState.currentPageOffset)

        alpha = lerp(
            start = 0.4F,
            stop = 1F,
            fraction = 1 - pageOffset.absoluteValue.coerceIn(0F, 1F)
        )

        cameraDistance = 8 * density
        rotationY = lerp(
            start = 0f,
            stop = 40f,
            fraction = pageOffset.coerceIn(-1f, 1f),
        )

        lerp(
            start = 0.5f,
            stop = 1f,
            fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
        ).also { scale ->
            scaleX = scale
            scaleY = scale
        }
    }
)
