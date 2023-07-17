package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class FABActivity : ComponentActivity() {
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
                        IconFAB()
                        ColouredFAB()
                        ElevatedFAB()
                        RectangularFAB()
                        ExtendedFAB()
                        BadgeBoxDemo()
                    }
                }
            }
        }
    }
}

@Composable
fun IconFAB() {
    FloatingActionButton(onClick = {}) { Icon(Icons.Outlined.Star, "Icon FAB") }
}

@Composable
fun ColouredFAB() {
    FloatingActionButton(onClick = {}, contentColor = Color.White, containerColor = Color.Red) {
        Icon(Icons.Outlined.Star, "Coloured FAB")
    }
}

@Composable
fun ElevatedFAB() {
    FloatingActionButton(
        onClick = {},
        shape = CutCornerShape(15.dp),
        elevation = FloatingActionButtonDefaults.elevation(20.dp)
    ) { Icon(Icons.Outlined.Star, "Elevated FAB") }
}

@Composable
fun RectangularFAB() {
    FloatingActionButton(onClick = {}, shape = RectangleShape) {
        Icon(Icons.Outlined.Star, "Rectangular FAB")
    }
}

@Composable
fun ExtendedFAB() {
    ExtendedFloatingActionButton(
        text = { Text("Extended FAB") },
        icon = { Icon(Icons.Default.Message, "Extended FAB") },
        onClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgeBoxDemo() {
    BadgedBox(badge = {
        Badge { Text("8") }
    }) { Icon(Icons.Default.Favorite, "Favourite with notification") }
}
