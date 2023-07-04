package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(10) { "$it" }
) {
    /*
    LazyColumn and LazyRow are equivalent to RecyclerView in Android Views.
    In its basic usage, the LazyColumn API provides an items element within its scope,
    where individual item rendering logic is written:
     */
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        //for(name in names)
        items(items = names) { name ->
            Greeting(name = "$name!")
        }
    }
}

@Composable
private fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) } // State and MutableState are interfaces that hold some value and trigger UI updates (recompositions) whenever that value changes.
    // remember is used to guard against recomposition, so the state is not reset.
    /*The reason why mutating this variable does not trigger recompositions is that it's not being tracked by Compose.
    Also, each time Greeting is called, the variable will be reset to false.
    To add internal state to a composable, you can use the mutableStateOf function,
    which makes Compose recompose functions that read that State.*/

    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            // animateDpAsState takes an optional animationSpec parameter that lets you customize the animation.
            // Let's do something more fun like adding a spring-based animation:
            /*Any animation created with animate*AsState is interruptible.
            This means that if the target value changes in the middle of the animation,
            animate*AsState restarts the animation and points to the new value.
            Interruptions look especially natural with spring-based animations*/
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
        ) {
            Text(text = "Hello ")
            Text(
                text = "$name!",
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold)
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy.\n").repeat(4)
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector =
                if (expanded)
                    Icons.Filled.ExpandLess
                else
                    Icons.Filled.ExpandMore,
                contentDescription =
                if (expanded)
                    stringResource(R.string.show_less)
                else
                    stringResource(R.string.show_more)
            )
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    FirstjetcomposeTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    // Instead of using remember you can use rememberSaveable.
    // This will save each state surviving configuration changes (such as rotations) and process death.
    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingsPreview() {
    FirstjetcomposeTheme {
        Greetings()
    }
}

/*
In Composable functions, state that is read or modified by multiple functions should live in
a common ancestor—this process is called state hoisting.
To hoist means to lift or elevate.
*/

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    FirstjetcomposeTheme {
        OnboardingScreen({})
        // Assigning onContinueClicked to an empty lambda expression means "do nothing", which is perfect for a preview.
    }
}
