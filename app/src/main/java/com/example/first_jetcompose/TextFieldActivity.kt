package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class EditTextActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TxtFieldLayout()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TxtFieldLayout() {
    val inputValue =
        remember { mutableStateOf(TextFieldValue()) } // For getting a value of our text field.

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Outlined Text Field with Leading and Trailing icons
            OutlinedTextField(
                value = inputValue.value,
                onValueChange = { inputValue.value = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                placeholder = { Text("Enter username") },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text
                ),
                textStyle = TextStyle(
                    color = Color.Black, fontSize = TextUnit.Unspecified,
                    fontFamily = FontFamily.SansSerif
                ),
                maxLines = 2,
                singleLine = true,
                label = { Text("Username") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Leading Icon",
                        tint = colorResource(R.color.purple_200)
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Trailing Icon",
                        tint = colorResource(R.color.purple_200)
                    )
                }
            )

            Text(
                text = "Your username: " + inputValue.value.text,
                modifier = Modifier.padding(horizontal = 5.dp),
                style = TextStyle(background = Color.LightGray)
            )
        }

        // Text Field for Hide keyboard and loosing focus
        val keyboardController = LocalSoftwareKeyboardController.current // To hide keyboard
        val focusManager =
            LocalFocusManager.current // For hide keyboard and clear focus from the text-field

        TextField(
            value = inputValue.value,
            onValueChange = { inputValue.value = it },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            label = { Text(text = "Number") },
            placeholder = { Text(text = "Enter your number") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )
        )

        // Text field with password masking
        var showPassword by remember { mutableStateOf(false) }
        TextField(
            value = inputValue.value,
            onValueChange = { inputValue.value = it },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter your password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock Icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = if (showPassword) "Show Password" else "Hide Password"
                    )
                }
            },
            // Toggle password masking
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
        )

        // Text field with validation
        var age by remember { mutableStateOf("") }
        var isBelow18 by remember { mutableStateOf(false) }

        Column {
            TextField(
                value = age,
                onValueChange = {
                    age = it
                    isBelow18 = false
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                label = { Text("Age") },
                placeholder = { Text(text = "Enter your age") },
                isError = isBelow18,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        isBelow18 = validateAge(age)
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            )
            if (isBelow18) {
                Text(
                    text = "You should be 18+",
                    modifier = Modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        // Text field with gradient colors
        TextField(
            value = inputValue.value,
            onValueChange = { inputValue.value = it },
            label = { Text("Name") },
            placeholder = { Text("Enter your name") },
            /*colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Green.copy(0.4f),
                cursorColor = Color.Yellow
            ),*/
            // For Gradient Background
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(listOf(Color.Green, Color.Yellow, Color.Red))
                )
        )

        // Multiline Text field with center aligned text
        var multilineValue by remember { mutableStateOf(TextFieldValue()) }
        TextField(
            value = multilineValue,
            onValueChange = { multilineValue = it },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            singleLine = false,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
        )
    }
}

private fun validateAge(age: String): Boolean {
    return age.toInt() < 18
}

@Preview(showBackground = true)
@Composable
fun TextPreview() {
    TxtFieldLayout()
}
