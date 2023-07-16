package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.math.abs
import kotlin.math.absoluteValue

class AccountSwitcherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchBar()
                }
            }
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    var selectedAccount by remember { mutableStateOf(accounts[0]) }

    Card(
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xD22E2C37)),
        modifier = modifier.wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.padding(end = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Menu, contentDescription = "Menu",
                    tint = Color.White
                )
            }
            Text(
                text = "Search in mail",
                color = Color(0xFFBCBAC5),
                modifier = Modifier.weight(1f)
            )
            AccountSwitcher(
                accounts = accounts,
                currentAccount = selectedAccount,
                onAccountChanged = { selectedAccount = it }
            )
        }
    }
}

data class Account(@DrawableRes val image: Int)

// https://github.com/victorbrndls/BlogProjects/blob/gmail-account-switch/
private val accounts = listOf(
    Account(image = R.drawable.dog1),
    Account(image = R.drawable.dog2),
    Account(image = R.drawable.dog3)
)

@Composable
fun AccountSwitcher(
    accounts: List<Account>,
    currentAccount: Account,
    onAccountChanged: (Account) -> Unit,
) {
    val imageSize = 36.dp // The size for the account image
    val imageSizePx =
        with(LocalDensity.current) { imageSize.toPx() } // imageSize in Pixels for animations
    val currentAccountIndex = accounts.indexOf(currentAccount)
    var nextAccountIndex by remember { mutableStateOf<Int?>(null) }
    var delta by remember(currentAccountIndex) { mutableFloatStateOf(0F) }
    val draggableState = rememberDraggableState(onDelta = { delta = it })
    val targetAnimation = remember { Animatable(0F) }

    // The code inside LaunchedEffect is what makes the animation happen
    LaunchedEffect(key1 = currentAccountIndex) {
        snapshotFlow { delta } // Observe the mutableState as a Flow
            .filter { nextAccountIndex == null } // if nextAccountIndex is null, then no animation is happening
            .filter { it.absoluteValue > 1F }// Keep delta greater than 1 for ignoring multiple or accidental scrolls
            .throttleFirst(300) // to only receive 1 value every 300ms, that’s because delta changes a lot when you swipe and we only wanted the first value.
            .map { delta ->
                // Check if the scroll is possible and return
                // +1 if the user scrolled down and we need to animate to the next account,
                // -1 if the user scrolled up and we need to animate to the previous account, or
                // 0 if there’s no account before or after
                if (delta < 0) {
                    if (currentAccountIndex < accounts.size - 1) 1 else 0
                } else {
                    if (currentAccountIndex > 0) -1 else 0
                }
            }
            .filter { it != 0 } // filter only values if the change != 0, don’t animate anything if nothing changed
            .collect { change ->
                nextAccountIndex = currentAccountIndex + change

                targetAnimation.animateTo(
                    change.toFloat(),
                    animationSpec = tween(easing = LinearEasing, durationMillis = 200)
                )

                onAccountChanged(accounts[nextAccountIndex!!]) // notify the parent that account changed after the animation ends
                nextAccountIndex = null
                targetAnimation.snapTo(0F)
            }
    }
    Box(modifier = Modifier.size(imageSize)) {
        nextAccountIndex?.let { index ->
            Image(
                painter = painterResource(id = accounts[index].image), null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .graphicsLayer {
                        // Getting absolute for the animation
                        scaleX = abs(targetAnimation.value)
                        scaleY = abs(targetAnimation.value)
                    }
                    .clip(CircleShape)
            )
        }
        Image(
            painter = painterResource(id = accounts[currentAccountIndex].image), null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Vertical
                )
                .graphicsLayer {
                    this.translationY = targetAnimation.value * imageSizePx * -1.5F
                }
                .clip(CircleShape)
        )
    }
}

// https://proandroiddev.com/from-rxjava-to-kotlin-flow-throttling-ed1778847619
fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
    require(periodMillis > 0) { "period should be positive" }
    return flow {
        var lastTime = 0L
        collect { value ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime >= periodMillis) {
                lastTime = currentTime
                emit(value)
            }
        }
    }
}
