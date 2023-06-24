package com.example.first_jetcompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FullScreenDialogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FullScreenDialog()
                    DialogBoxLoadingLayout()
                    DialogBoxDeleteLayout()
                    DialogBoxDelete2Layout()
                    TwoFADialogLayout()
                }
            }
        }
    }
}

@Composable
fun FullScreenDialog(context: Context = LocalContext.current.applicationContext) {
    var dialogOpen by remember { mutableStateOf(false) }

    if (dialogOpen) {
        Dialog(
            onDismissRequest = { dialogOpen = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_medal),
                        contentDescription = "Medal",
                        tint = Color(0xFF35898F),
                        modifier = Modifier.size(150.dp)
                    )

                    Text(
                        text = "Congratulations!! You've won a medal.",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 20.dp)
                    )

                    Button(
                        onClick = { Toast.makeText(context, "Shared", Toast.LENGTH_SHORT).show() },
                        modifier = Modifier.padding(top = 20.dp),
                        shape = RoundedCornerShape(percent = 25)
                    ) { Text("Share", fontSize = 18.sp) }
                } // Column
            } // Surface
        } // Dialog
    } // if
    Button(onClick = { dialogOpen = true }) { Text("Open Fullscreen Dialog") }
}

// Loading Dialog Box
@Composable
fun DialogBoxLoadingLayout() {
    Column(
        modifier = Modifier.background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val viewModel: MyViewModel = viewModel()
        val openDialog by viewModel.open.observeAsState(false)

        Button(onClick = { viewModel.open.value = true }) { Text("Open Loading Dialog") }

        if (openDialog) {
            viewModel.startThread()
            DialogBoxLoading()
        }
    }
}

@Composable
fun DialogBoxLoading(
    progressIndicatorColor: Color = Color(0xFF35898f),
    progressIndicatorSize: Dp = 80.dp,
) {
    Dialog(onDismissRequest = {}) {
        Surface(elevation = 4.dp, shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier.padding(start = 56.dp, end = 56.dp, top = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProgressIndicatorLoading(
                    progressIndicatorSize = progressIndicatorSize,
                    progressIndicatorColor = progressIndicatorColor
                )
                Spacer(modifier = Modifier.height(32.dp)) // Gap between progress indicator and text
                Text(
                    modifier = Modifier.padding(bottom = 32.dp),
                    text = "Please wait...",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                )
            }
        } // Surface
    } // Dialog
}

@Composable
fun ProgressIndicatorLoading(
    progressIndicatorSize: Dp,
    progressIndicatorColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = keyframes { durationMillis = 600 })
    )

    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier
            .size(progressIndicatorSize)
            .rotate(angle)
            .border(
                width = 12.dp,
                brush = Brush.sweepGradient(
                    listOf(
                        Color.White, // add background color first
                        progressIndicatorColor.copy(alpha = 0.1f),
                        progressIndicatorColor
                    )
                ),
                shape = CircleShape
            ),
        strokeWidth = 1.dp,
        color = Color.White // Set background color
    )
}

class MyViewModel : ViewModel() {
    var open = MutableLiveData<Boolean>()

    fun startThread() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                // Background work
                delay(3000)
            }

            closeDialog()
        }
    }

    private fun closeDialog() {
        open.value = false
    }
}

// Delete item Dialog
@Composable
fun DialogBoxDeleteLayout() {
    Column(
        modifier = Modifier.background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var openDialog by remember { mutableStateOf(false) }
        Button(onClick = { openDialog = true }) { Text("Open Delete Dialog") }
        if (openDialog) {
            DialogBoxDelete {
                openDialog = false
            }
        }
    }
}

@Composable
fun DialogBoxDelete(onDismiss: () -> Unit) {
    val context = LocalContext.current.applicationContext
    val interactionSource = remember { MutableInteractionSource() } // To disable the Ripple Effect
    val buttonCorner = 6.dp

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier.wrapContentHeight(),
            shape = RoundedCornerShape(size = 12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 6.dp,
                        alignment = Alignment.Start
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        painter = painterResource(id = R.drawable.trash_2),
                        contentDescription = "Delete Icon",
                        tint = Color(0xFFFF0000)
                    )

                    Text(
                        text = "Delete Item?",
                        style = TextStyle(
                            color = Color.Black.copy(alpha = 0.87f),
                            fontSize = 20.sp
                        ),
                    )
                } // Icon-Title Row

                // Message Text
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 20.dp),
                    text = "Are you sure you want to delete this item from the list?",
                    style = TextStyle(
                        color = Color.Black.copy(alpha = 0.95f),
                        fontSize = 16.sp,
                        lineHeight = 22.sp
                    ),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 10.dp,
                        alignment = Alignment.End
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .clickable(
                                indication = null, // To disable the ripple effect
                                interactionSource = interactionSource
                            ) {
                                Toast
                                    .makeText(context, "Cancel", Toast.LENGTH_SHORT)
                                    .show()
                                onDismiss()
                            }
                            .border(
                                width = 1.dp,
                                color = Color(0xFF35898F),
                                shape = RoundedCornerShape(buttonCorner)
                            )
                            .padding(top = 6.dp, bottom = 8.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Text(
                            text = "Cancel",
                            style = TextStyle(fontSize = 16.sp),
                            color = Color(0xFF35898F)
                        )
                    } // Cancel Button

                    // Delete button
                    Box(
                        modifier = Modifier
                            .clickable(
                                // This is to disable the ripple effect
                                indication = null,
                                interactionSource = interactionSource
                            ) {
                                Toast
                                    .makeText(context, "Delete", Toast.LENGTH_SHORT)
                                    .show()
                                onDismiss()
                            }
                            .background(
                                color = Color(0xFFFF0000),
                                shape = RoundedCornerShape(buttonCorner)
                            )
                            .padding(top = 6.dp, bottom = 8.dp, start = 24.dp, end = 24.dp),
                    ) {
                        Text(
                            text = "Delete",
                            style = TextStyle(fontSize = 16.sp),
                            color = Color.White
                        )
                    } // Delete Button
                } // Button Row
            } // Column
        } // Surface
    } // Dialog
}

// Second Delete Dialog
@Composable
fun DialogBoxDelete2Layout(
    negativeButtonColor: Color = Color(0xFF35898F),
    positiveButtonColor: Color = Color(0xFFFF0000),
    context: Context = LocalContext.current.applicationContext
) {
    var dialogOpen by remember { mutableStateOf(false) }

    if (dialogOpen) {
        Dialog(onDismissRequest = { dialogOpen = false }) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent // making column's parent transparent
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .padding(30.dp) // Empty space at the top
                            .fillMaxWidth()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(percent = 10)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(36.dp))
                        Text(
                            text = "Delete?",
                            fontSize = 24.sp
                        )

                        Spacer(Modifier.height(18.dp))
                        Text(
                            text = "Are you sure, you want to delete the item?",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontSize = 18.sp
                        )

                        Spacer(Modifier.height(18.dp))
                        Row( // Button row
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            DialogButton(buttonColor = negativeButtonColor, buttonText = "No") {
                                Toast.makeText(context, "No Click", Toast.LENGTH_SHORT).show()
                                dialogOpen = false
                            }
                            DialogButton(buttonColor = positiveButtonColor, buttonText = "Yes") {
                                Toast.makeText(context, "Yes Click", Toast.LENGTH_SHORT).show()
                                dialogOpen = false
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp /* * 2*/)) // If you decrease the Surface's width, increase this height
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.trash_2),
                        contentDescription = "Delete icon",
                        tint = positiveButtonColor,
                        modifier = Modifier
                            .background(color = Color.White, shape = CircleShape)
                            .border(width = 2.dp, shape = CircleShape, color = positiveButtonColor)
                            .padding(all = 16.dp)
                            .align(alignment = Alignment.TopCenter)
                    )
                }
            }
        }
    }
    Button(onClick = { dialogOpen = true }) { Text(text = "Open Second Delete Dialog") }
}

@Composable
fun DialogButton(buttonColor: Color, buttonText: String, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                color = buttonColor,
                shape = RoundedCornerShape(percent = 26)
            )
            .clickable { onDismiss() }
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = buttonText,
            color = Color.White,
            fontSize = 18.sp,
        )
    }
}

// 2FA Dialog box
@Composable
fun TwoFADialogLayout() {
    Column(
        modifier = Modifier.background(color = MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var openDialog by remember { mutableStateOf(false) }

        Button(onClick = { openDialog = true }) {
            Text("Open 2FA Dialog")
        }

        if (openDialog) {
            DialogBox2FA {
                openDialog = false
            }
        }
    }
}

@Composable
fun DialogBox2FA(onDismiss: () -> Unit) {
    val contextForToast = LocalContext.current.applicationContext

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(color = Color(0xFF35898f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        painter = painterResource(R.drawable.image_security),
                        contentDescription = "2-Step Verification",
                        alignment = Alignment.Center
                    )
                }
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = "2-step Verification",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 20.sp)
                )

                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = "Setup 2-step Verification to add additional layer of security to your account.",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 14.sp)
                )

                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp, start = 36.dp, end = 36.dp, bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF35898f)),
                    onClick = {
                        onDismiss()
                        Toast.makeText(contextForToast, "Setting up", Toast.LENGTH_SHORT).show()
                    }) {
                    Text(
                        text = "Setup Now",
                        color = Color.White,
                        style = TextStyle(fontSize = 16.sp)
                    )
                }

                TextButton(onClick = { onDismiss() }) {
                    Text(
                        text = "I'll od it later",
                        modifier = Modifier.padding(bottom = 10.dp),
                        color = Color(0xFF35898F),
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
        }
    }
}
