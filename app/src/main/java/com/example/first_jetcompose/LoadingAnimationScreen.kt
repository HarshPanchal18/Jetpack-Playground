package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import kotlinx.coroutines.delay

class LoadingAnimationScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircleLoadingAnimation()
                    DotLoadingAnimation()
                    RippleLoadingAnimation()
                    GradientLoadingAnimation()
                    LinearProgressIndicatorSample()
                }
            }
        }
    }
}

@Composable
fun CircleLoadingAnimation(
    circleColor: Color = Color.Magenta,
    animationDelay: Int = 1000
) {
    var circleScale by remember { mutableFloatStateOf(0f) }

    val circleStateAnimation = animateFloatAsState(
        targetValue = circleScale,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDelay)
        )
    )

    LaunchedEffect(Unit) { circleScale = 1f }

    // Animating Circle
    Box(
        modifier = Modifier
            .size(64.dp)
            .scale(circleStateAnimation.value)
            .border(
                width = 4.dp, color = circleColor.copy(alpha = 1 - circleStateAnimation.value),
                shape = CircleShape
            )
    )
}

@Composable
fun DotLoadingAnimation(
    circleColor: Color = Color(0xFF35898F),
    circleSize: Dp = 36.dp,
    animationDelay: Int = 400,
    initialAlpha: Float = 0.3f
) {
    // 3 Circles
    val circles = listOf(
        remember { Animatable(initialValue = initialAlpha) },
        remember { Animatable(initialValue = initialAlpha) },
        remember { Animatable(initialValue = initialAlpha) },
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(Unit) {
            delay(timeMillis = (animationDelay / circles.size).toLong() * index)

            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = animationDelay),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    // Container for 3 Circles
    Row {
        circles.forEachIndexed { index, animatable ->
            if (index != 0) Spacer(Modifier.width(6.dp)) // Gap between circle

            Box(
                modifier = Modifier
                    .size(circleSize)
                    .clip(CircleShape)
                    .background(color = circleColor.copy(animatable.value))
            )
        }
    }
}

@Composable
fun RippleLoadingAnimation(
    circleColor: Color = Color.Magenta,
    animationDelay: Int = 1500
) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(Unit) {
            delay(timeMillis = (animationDelay / circles.size).toLong() * index + 1) // Divide the animation delay by number of circles

            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDelay,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart // Restart the animation and animate from the startValue to the endValue
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.Transparent)
    ) {
        circles.forEachIndexed { _, animatable ->
            Box(
                modifier = Modifier
                    .scale(animatable.value)
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(circleColor.copy(alpha = (1 - animatable.value)))
            )
        }
    }
}

@Composable
fun GradientLoadingAnimation(
    indicatorSize: Dp = 100.dp,
    circleColors: List<Color> = listOf(
        Color(0xFF5851D8),
        Color(0xFF833AB4),
        Color(0xFFC13584),
        Color(0xFFE1306C),
        Color(0xFFFD1D1D),
        Color(0xFFF56040),
        Color(0xFFF77737),
        Color(0xFFFCAF45),
        Color(0xFFFFDC80),
        Color(0xFF5851D8),
    ),
    animationDuration: Int = 360
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotateAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration,
                easing = LinearEasing
            )
        )
    )

    CircularProgressIndicator(
        modifier = Modifier
            .size(indicatorSize)
            .rotate(degrees = rotateAnimation)
            .border(
                width = 4.dp,
                brush = Brush.sweepGradient(circleColors),
                shape = CircleShape
            ),
        progress = 1f,
        strokeWidth = 1.dp,
        color = MaterialTheme.colorScheme.background
    )
}

@Composable
fun LinearProgressIndicatorSample() {
    var progress by remember { mutableFloatStateOf(0.1F) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(10.dp))
        Text("LinearProgressIndicator with undefined progress")
        LinearProgressIndicator()

        Spacer(Modifier.height(20.dp))
        Text("LinearProgressIndicator with progress set by button")
        LinearProgressIndicator(progress = animatedProgress)

        Spacer(Modifier.height(10.dp))
        Row {
            OutlinedButton(onClick = { if (progress < 1f) progress += 0.1f }) { Text("Increase") }
            OutlinedButton(onClick = { if (progress > 0f) progress -= 0.1f }) { Text("Decrease") }
        }
    }
}
