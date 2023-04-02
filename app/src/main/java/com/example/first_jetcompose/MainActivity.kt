package com.example.first_jetcompose

import android.content.Context
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import com.example.first_jetcompose.ui.theme.SampleData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {  // A surface container using the 'background' color from the theme
                    Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
                        Conversation(SampleData.conversationSample)
                    } // Column
                } // Surface
            } // FirstjetcomposeTheme
        } // setContent
    }
}

data class Message(val author:String,val body:String)

@Composable
fun MessageCard(msg:Message) {
    val mContext = LocalContext.current // Fetching the local context for using the Toast
    Row(
        modifier = Modifier.padding(5.dp).fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(10.dp))
            .background("#a0ca74".color).clickable { mToast(mContext) },
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.padding(5.dp).background(Color.LightGray, shape = CircleShape)
                .border(2.dp, MaterialTheme.colors.secondary, CircleShape))
        Spacer(modifier = Modifier.width(8.dp)) // Add a horizontal space between the image and the column
        var isExpanded by remember { mutableStateOf(false) } // We keep track if the message is expanded or not in this variable
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface)
        Column(modifier = Modifier.align(Alignment.CenterVertically)
            .clickable { isExpanded = !isExpanded }) {
            Text(text = msg.author,
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(4.dp)) // Add a vertical space between the author and message texts
            Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(text = msg.body,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1)
            }
        }
    }
} // MessageCard

// Function to generate a Toast
private fun mToast(context: Context) {
    Toast.makeText(context, "Toasted", Toast.LENGTH_LONG).show()
}

@Composable
fun Conversation(message:List<Message>) {
    LazyColumn {
        items(message) { MessageCard(msg = it) }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    FirstjetcomposeTheme {
        Conversation(SampleData.conversationSample)
    }
}

// Adding extension for apply the HexCoded color
// .background("HEXCODE".color)
val String.color get()=Color(parseColor(this))
