@file:OptIn(ExperimentalComposeUiApi::class)
@file:Suppress("UNUSED_EXPRESSION", "OPT_IN_IS_NOT_ENABLED")

package com.example.first_jetcompose

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color.parseColor
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import com.example.first_jetcompose.ui.theme.SampleData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                TitleContent()
            } // FirstjetcomposeTheme
        } // setContent
    }
}

data class Message(val author: String, val body: String)

@Preview
@Composable
fun TitleContent() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        color = Color.Black,
                        fontFamily = FontFamily.Cursive,
                    )
                },
                backgroundColor = Color(0xff0f9d58)
            )
        },
        content = {it
            Column(
                //modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                StartActivityButton(
                    text = "Expand Activity",
                    intentActivity = SecondActivity::class.java
                )

                StartActivityButton(
                    text = "Corner Activity",
                    intentActivity = BorderActivity::class.java
                )

                StartActivityButton(
                    text = "Drag Activity",
                    intentActivity = DraggableActivity::class.java
                )

                StartActivityButton(
                    text = "Row Grid Activity",
                    intentActivity = LazyGrid::class.java
                )

                StartActivityButton(
                    text = "Graphics Layer Activity",
                    intentActivity = GraphicsLayer::class.java
                )

                StartActivityButton(
                    text = "Image Activity",
                    intentActivity = ImageActivity::class.java
                )

                StartActivityButton(
                    text = "Text To Speech Activity",
                    intentActivity = TextToSpeeches::class.java
                )

                HomeContent()
            }
        })
}

@Composable
fun HomeContent() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {  // A surface container using the 'background' color from the theme
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Conversation(SampleData.conversationSample)
        } // Column
    } // Surface
}

@Composable
fun MessageCard(msg: Message) {
    val mContext = LocalContext.current // Fetching the local context for using the Toast
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(10.dp))
            .background("#a0ca74".color)
            .clickable { mToast(mContext) },
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .padding(5.dp)
                .background(Color.LightGray, shape = CircleShape)
                .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp)) // Add a horizontal space between the image and the column
        var isExpanded by remember { mutableStateOf(false) } // We keep track if the message is expanded or not in this variable

        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface)

        Column(modifier = Modifier
            .align(Alignment.CenterVertically)
            .clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp)) // Add a vertical space between the author and message texts
            Surface(
                shape = MaterialTheme.shapes.medium, elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }
} // MessageCard

// Function to generate a Toast
private fun mToast(context: Context) {
    Toast.makeText(context, "Toasted", Toast.LENGTH_LONG).show()
}

@Composable
fun Conversation(message: List<Message>) {
    LazyColumn {
        items(message) { MessageCard(msg = it) }
    }
}

@Composable
fun StartActivityButton(
    text: String,
    intentActivity: Class<out Activity>,
    mContext: Context = LocalContext.current, // Fetching the local context for using the Toast
) {
    Button(
        onClick = {
            mContext.startActivity(Intent(mContext, intentActivity))
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        //.align(Alignment.CenterHorizontally),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))
    ) {
        Text(text = text, color = Color.White)
    }
}

// Adding extension for apply the HexCoded color
// .background("HEXCODE".color)
val String.color get() = Color(parseColor(this))
