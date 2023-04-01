package com.example.first_jetcompose

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize()
                            .padding(5.dp),
                    ) {
                        for (i in 1..10) {
                            MessageCard(Message("Android", "$i. Jetpack Compose\n"))
                        }
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
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(10.dp))
            .background(Color.Yellow)
            .clickable { mToast(mContext) },
        horizontalArrangement = Arrangement.Center,
        ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.padding(5.dp)
                .background(Color.LightGray, shape = CircleShape)
                .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp)) // Add a horizontal space between the image and the column
        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp)) // Add a vertical space between the author and message texts
            Surface(shape = MaterialTheme.shapes.small, elevation = 3.dp) {
                Text(
                    text = msg.body,
                    style = MaterialTheme.typography.body1,
                    //modifier = Modifier.padding(4.dp).fillMaxWidth()
                )
            }
        }
    }
}

// Function to generate a Toast
private fun mToast(context: Context) {
    Toast.makeText(context, "Toasted", Toast.LENGTH_LONG).show()
}

@Preview(name = "Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true)
@Composable
fun PreviewCard() {
    FirstjetcomposeTheme {
        MessageCard(Message("Collage", "SDJ International College, Vesu"))
    }
}

/*
@Composable
fun Greeting(name: String) {
    Box(
        modifier = Modifier.size(300.dp)
    ) {
        Column {
            Text(
                text = "Hello $name!",
                color = Color.Cyan,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Just started the Jetpack",
                color = Color.Green
            )
        }
    }
}

@Composable
fun Images() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.background(Color.LightGray),
        )
        Icon( // for the vectors
            imageVector = Icons.Default.AccountBox,
            contentDescription = null,
            modifier = Modifier
                .background(Color.Blue)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirstjetcomposeTheme {
        Greeting("Android")
        Images()
    }
}

//@Preview
@Composable
private fun IconFloatingActionButton() {
    FloatingActionButton(onClick = {},
    shape = RectangleShape) {
        Icon(
            imageVector = Icons.Outlined.Star,
            contentDescription = null
        )
    }
}
*/
