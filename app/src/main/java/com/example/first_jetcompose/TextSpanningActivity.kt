package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class TextSpanningActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ImageWithoutBounds(id = R.drawable.dog2)

                        val annotatedLinkString = buildAnnotatedString {
                            val text = "Click here to connect with developer."
                            val startIndex = text.indexOf("here")
                            val endIndex = startIndex + 4

                            append(text = text)
                            addStyle(
                                style = SpanStyle(
                                    color = Color.Blue,
                                    textDecoration = TextDecoration.Underline
                                ), start = startIndex, end = endIndex
                            )

                            // Attach a string annotation that stores a URL to the "here"
                            addStringAnnotation(
                                tag = "URL",
                                annotation = "https://github.com/HarshPanchal18/",
                                start = startIndex,
                                end = endIndex
                            )
                        }
                        val uriHandler = LocalUriHandler.current
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            ClickableText(
                                text = annotatedLinkString,
                                style = TextStyle(fontSize = 20.sp),
                                onClick = {
                                    annotatedLinkString
                                        .getStringAnnotations("URL", it, it)
                                        .firstOrNull()?.let { stringAnnotation ->
                                            uriHandler.openUri(stringAnnotation.item)
                                        }
                                },
                            )
                        } // Text Span Column
                    } // Parent Column
                } // Surface
            }
        }
    }
}

@Composable
fun ImageWithoutBounds(@DrawableRes id: Int) {
    val painter = painterResource(id = id)
    Image(
        modifier = Modifier
            //.wrapContentSize(align = Alignment.TopCenter, unbounded = true)
            //.wrapContentWidth(align = Alignment.Start, unbounded = true)
            //.wrapContentHeight(align = Alignment.Bottom, unbounded = true)
            .clip(RoundedCornerShape(percent = 10))
            .border(width = 5.dp,color = Color.Gray, RoundedCornerShape(percent = 10))
            .size(300.dp),
        painter = painter,
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}
