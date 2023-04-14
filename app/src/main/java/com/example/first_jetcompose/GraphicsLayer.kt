package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class GraphicsLayer : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    GraphicsLayerScreen()
                }
            }
        }
    }
}

@Preview
@Composable
private fun GraphicsLayerScreen() {
    Scaffold {
        Column(modifier = Modifier
            .background("#aa54a2da".color)
            .fillMaxSize()
            .padding(vertical = 42.dp)
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GraphicsLayerScaleX()
            GraphicsLayerScaleY()
            GraphicsLayerAlpha()
            GraphicsLayerTranslationX()
            GraphicsLayerTranslationY()
            GraphicsLayerShadowElevation()
            GraphicsLayerRotationX()
            GraphicsLayerRotationY()
            GraphicsLayerRotationZ()
            GraphicsLayerCameraDistance()
            GraphicsLayerTransformOrigin()
            CustomText()
            CustomText3()
        }
    }
}

@Composable
fun GraphicsLayerScaleX() {
    Text(
        text = stringResource(id = R.string.app_name),
        Modifier
            .graphicsLayer(
                scaleX = 1.5f,
                clip = true,
            )
            .width(200.dp),
        fontWeight = FontWeight.SemiBold,
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Justify
    )
}

@Composable
fun CustomText() {
    Text(
        buildAnnotatedString {
            withStyle(ParagraphStyle(textAlign = TextAlign.Center)
            ) {
                withStyle(SpanStyle(color = MaterialTheme.colors.primary,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold)) {
                    append("A")
                }
                append("B")
                append("C")
                append("D")
            }
        }, modifier = Modifier.width(200.dp)
    )
}

@Composable
fun CustomText3() {
    Text("Hello World! ".repeat(30), maxLines = 2, overflow = TextOverflow.Ellipsis)
}

@Composable
fun GraphicsLayerScaleY() {
    Text(stringResource(id = R.string.app_name),
        Modifier.graphicsLayer(
            scaleY = 1.5f,
            clip = true
        ))
}

@Composable
fun GraphicsLayerAlpha() {
    Text(stringResource(id = R.string.app_name),
        Modifier.graphicsLayer(
            alpha = 0.5f,
            clip = true
        ))
}

@Composable
fun GraphicsLayerTranslationX() {
    Text(stringResource(id = R.string.app_name),
        Modifier.graphicsLayer(
            translationX = 0.5f,
            clip = true
        ))
}

@Composable
fun GraphicsLayerTranslationY() {
    Text(stringResource(id = R.string.app_name),
        Modifier.graphicsLayer(
            translationY = 0.5f,
            clip = true
        ))
}

@Composable
fun GraphicsLayerShadowElevation() {
    Text(stringResource(id = R.string.app_name),
        Modifier.graphicsLayer(
            shadowElevation = 3.5f,
            clip = true
        ))
}

@Composable
fun GraphicsLayerRotationX() {
    Text(stringResource(id = R.string.app_name),
        Modifier.graphicsLayer(
            rotationX = 180f,
            clip = true
        ))
}

@Composable
fun GraphicsLayerRotationY() {
    Text(stringResource(id = R.string.app_name),
        Modifier.graphicsLayer(
            rotationY = 180f,
            clip = true
        ))
}

@Composable
fun GraphicsLayerRotationZ() {
    Text(stringResource(id = R.string.app_name),
        Modifier.graphicsLayer(
            rotationZ = 330f,
            clip = true
        ))
}

@Composable
fun GraphicsLayerCameraDistance() {
    Text(stringResource(id = R.string.app_name),
        Modifier.graphicsLayer(
            cameraDistance = 16f,
            clip = true
        ))
}

@Composable
fun GraphicsLayerTransformOrigin() {
    Text(stringResource(id = R.string.app_name),
        Modifier.graphicsLayer(
            transformOrigin = TransformOrigin(2f, 2f),
            clip = true
        ))
}
