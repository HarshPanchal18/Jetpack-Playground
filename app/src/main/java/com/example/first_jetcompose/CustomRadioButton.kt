package com.example.first_jetcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class CustomRadioButton : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Select a Dog:",
                            modifier = Modifier.padding(start = 16.dp),
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.height(height = 8.dp))
                        CustomRadioButtonLayout()
                    } // Column
                } // Surface
            } // Theme
        }
    }
}

@Composable
fun CustomRadioButtonLayout() {
    val dogList = arrayListOf(
        DogsData(image = R.drawable.dog1, name = "Frankie", age = "5-year", price = "300"),
        DogsData(image = R.drawable.dog2, name = "Lucky", age = "2-year", price = "500"),
        DogsData(image = R.drawable.dog3, name = "Prince", age = "3-year", price = "250"),
    )

    var selectedItem by remember { mutableStateOf(dogList[0].name) }
    val contextForToast = LocalContext.current.applicationContext

    Column(modifier = Modifier.selectableGroup()) {
        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            dogList.forEach { dogDetails ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (selectedItem == dogDetails.name),
                            onClick = {
                                selectedItem = dogDetails.name
                                Toast
                                    .makeText(contextForToast, dogDetails.name, Toast.LENGTH_SHORT)
                                    .show()
                            },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 8.dp)
                ) {
                    RadioButtonStyle(selectedItem = selectedItem, dogDetails = dogDetails)
                }
            }
        }
    }
}

@Composable
fun RadioButtonStyle(selectedItem: String, dogDetails: DogsData) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color =
                if (selectedItem == dogDetails.name) MaterialTheme.colorScheme.primary
                else Color.LightGray,
                shape = RoundedCornerShape(percent = 15)
            )
    ) {
        Row(Modifier.fillMaxWidth()) {
            // Dog image
            Image(painterResource(dogDetails.image), null, Modifier.size(94.dp))

            // Dog details
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 6.dp, bottom = 6.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Name
                    Text(dogDetails.name, fontSize = 22.sp)

                    // Check Icon
                    Icon(
                        imageVector = if (selectedItem == dogDetails.name) Icons.Outlined.CheckCircle
                        else Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = if (selectedItem == dogDetails.name) MaterialTheme.colorScheme.primary
                        else Color.Gray,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                } // Row

                // Age
                Text(
                    text = "${dogDetails.age}-old",
                    modifier = Modifier.padding(top = 4.dp),
                    fontSize = 18.sp
                )
                // Price
                Text(
                    text = "$${dogDetails.price}",
                    modifier = Modifier.padding(top = 6.dp),
                    fontSize = 20.sp
                )
            } // Column
        } // Row
    } // Box
}

object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}

data class DogsData(val image: Int, val name: String, val age: String, val price: String)
