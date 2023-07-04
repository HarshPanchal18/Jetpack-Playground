@file:Suppress("DEPRECATION")

package com.example.first_jetcompose

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class TransparentStatusBar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false) // expanded the app UI to system bars
        // instead of WindowCompat.setDecorFitsSystemWindows(), you can also use the following
        // window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    // set transparent color so that our image is visible behind the status bar
                    systemUiController.setStatusBarColor(color = Color.Transparent)
                    systemUiController.setNavigationBarColor(color = Color.Transparent)
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        val bitmap = BitmapFactory.decodeResource(
                            LocalContext.current.resources,
                            R.drawable.dog3
                        )

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                            LegacyBlurImage(bitmap, 25f)
                        } else {
                            BlurImage(
                                bitmap,
                                Modifier
                                    .fillMaxSize()
                                    .blur(radiusX = 15.dp, radiusY = 15.dp)
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Image(
                            painter = painterResource(id = R.drawable.dog3),
                            contentDescription = "Squirrel",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                } // Surface
            } // Theme
        }
    }
}

@Composable
fun LegacyBlurImage(
    bitmap: Bitmap,
    blurRadio: Float,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val renderScript = RenderScript.create(LocalContext.current)
    val bitmapAlloc = Allocation.createFromBitmap(renderScript, bitmap)
    ScriptIntrinsicBlur.create(renderScript, bitmapAlloc.element).apply {
        setRadius(blurRadio)
        setInput(bitmapAlloc)
        forEach(bitmapAlloc)
    }
    bitmapAlloc.copyTo(bitmap)
    renderScript.destroy()

    BlurImage(bitmap, modifier)
}

@Composable
fun BlurImage(bitmap: Bitmap, modifier: Modifier) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        //contentScale = ContentScale.Crop,
        modifier = modifier
    )
}
