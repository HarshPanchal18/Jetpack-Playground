package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
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
                    }
                }
            }
        }
    }
}
