@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package com.example.first_jetcompose

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color.parseColor
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.first_jetcompose.classes.TabRowItem
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import com.example.first_jetcompose.ui.theme.SampleData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                HomeLayout()
                var pressedTime: Long = 0
                BackHandler(enabled = true) {
                    if (pressedTime + 2000 > System.currentTimeMillis()) finish()
                    else Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT)
                        .show()
                    pressedTime = System.currentTimeMillis()
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeLayout() {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Home",
                    color = Color.Black,
                    fontFamily = FontFamily.Cursive,
                )
            },
            backgroundColor = Color(0xff0f9d58)
        )
    },
        content = {
            val activityButtons = listOf(
                "Expand Activity with Navigation" to SecondActivity::class.java,
                "Corner Activity" to BorderActivity::class.java,
                "Drag Activity" to DraggableActivity::class.java,
                "Row Grid Activity" to LazyGrid::class.java,
                "Graphics Layer Activity" to GraphicsLayer::class.java,
                "Image Activity" to ImageActivity::class.java,
                "Text To Speech Activity" to TextToSpeeches::class.java,
                "Auto Image Slider Activity" to AutoImageSlider::class.java,
                "Navigation Activity" to NavigationActivity::class.java,
                "Bottom Navigation Activity" to BottomNavigation::class.java,
                "Constraint Layout Activity" to ConstraintLayoutDemo::class.java,
                "Transparent Bars Activity" to TransparentStatusBar::class.java,
                "Shimmer Effect Activity" to ShimmerActivity::class.java,
                "Screen Orientation Activity" to ScreenOrientation::class.java,
                "Modern Bottom Navigation Activity" to CustomBottomNavigation::class.java,
                "Custom Switches Activity" to SwitchesActivity::class.java,
                "Bottom Sheet Activity" to BottomSheet::class.java,
                "Tab Layout Activity" to TabLayoutActivity::class.java,
                "TextField Activity" to EditTextActivity::class.java,
                "Navigation Drawer Activity" to NavigationDrawer::class.java,
                "Navigation Drawer with Bottom Navigation Activity" to BottomAndDrawerActivity::class.java,
                "Gradient Activity" to GradientActivity::class.java,
                "Swipe To Refresh Activity" to SwipeToRefresh::class.java,
                "OTP TextField Activity" to OtpTextField::class.java,
                "Circular Bottom Sheet Activity" to CircularRevealWap::class.java,
                "Chart Activity" to ChartScreen::class.java,
                "Dialog Activity" to AlertDialogActivity::class.java,
                "Drop Down Activity" to DropDownActivity::class.java,
                "Loading Animation Activity" to LoadingAnimationScreen::class.java,
                "Custom Dialog Activity" to FullScreenDialogActivity::class.java,
                "Custom List Radio Button Activity" to CustomRadioButton::class.java,
                "Custom Checkbox Activity" to CustomCheckboxActivity::class.java,
                "Pickers Activity" to PickerActivity::class.java,
                "Radios-Checks-Chips-Slider Activity" to RadioCheckActivity::class.java,
                "Custom Pager Indicators Activity" to CustomIndicators::class.java,
                "Floating Action Button Activity" to FABActivity::class.java,
                "Swipe to dismiss Activity" to SwipeToDismissActivity::class.java,
                "Markdown Text Activity" to MarkdownTextActivity::class.java,
                "Expandable Text Activity" to ExpandableTextActivity::class.java,
                "Text Spanning Activity" to TextSpanningActivity::class.java,
                "Segmented Control Activity" to SegmentedControlActivity::class.java,
                "Sticky Header Activity" to StickyHeaderActivity::class.java,
                "Account Switcher Activity" to AccountSwitcherActivity::class.java,
                "Custom Appbar Activity" to CustomAppbar::class.java,
                "Search Country Activity" to SearchCountryActivity::class.java,
                "Navigation with argument Activity" to NavigationWoSealedClassActivity::class.java,
                "Timeline Activity" to TimelineViewActivity::class.java,
                "Nested Navigation Activity" to NestedNavigationActivity::class.java,
                "Flows Activity" to FlowsActivity::class.java,
                "Horizontal Pager Activity" to HorizontalPagerActivity::class.java,
                "Bottom Navigation Question Activity" to BottomNavi::class.java,
                "Airplane Mode Broadcast Service Activity" to AirplaneModeActivity::class.java,
            )

            Column(modifier = Modifier.padding(it)) {
                Spacer(Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .weight(1.3F)
                        .padding(horizontal = 10.dp)
                ) {
                    LazyColumn {
                        items(activityButtons.reversed()) { (heading, destination) ->
                            StartActivityButton(heading, destination)
                        }
                    }
                }
                Column(modifier = Modifier.weight(1F)) { BottomList() }
            }
        } // content
    ) // Scaffold
}

@Composable
fun BottomList() { // Bottom list
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {  // A surface container using the 'background' color from the theme
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Conversation(SampleData.conversationSample)
        } // Column
    } // Surface
}

@Composable
fun MessageCard(msg: Message) {
    val mContext = LocalContext.current // Fetching the local context for using the Toast
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(10.dp))
            .background("#a0ca74".toColor)
            .clickable { mToast(mContext) },
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .padding(5.dp)
                .background(Color.LightGray, shape = CircleShape)
                .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(Modifier.width(8.dp)) // Add a horizontal space between the image and the column
        var isExpanded by remember { mutableStateOf(false) } // We keep track if the message is expanded or not in this variable

        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary
            else MaterialTheme.colors.surface,
            label = ""
        )

        Column(modifier = Modifier
            .align(Alignment.CenterVertically)
            .clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(4.dp)) // Add a vertical space between the author and message texts
            Surface(
                shape = MaterialTheme.shapes.medium, elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(4.dp),
                    // If the message is expanded, we display all its content otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }
} // MessageCard

// Function to generate a Toast
private fun mToast(context: Context) {
    Toast.makeText(context, "Toasted", Toast.LENGTH_LONG).show()
}

@Composable
fun Conversation(message: List<Message>) {
    LazyColumn {
        items(message) { MessageCard(msg = it) }
    }
}

@Composable
fun StartActivityButton(
    heading: String,
    destination: Class<out Activity>
) {
    val context = LocalContext.current
    val onClick = remember(context) { // To cache the onClick lambda expr
        { // This way, the lambda expression won't be recreated on every recomposition
            context.startActivity(Intent(context, destination))
        }
    }

    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))
    ) { Text(text = heading, color = Color.White) }
}

// Adding extension for apply the HexCoded color
// .background("HEX-CODE".color)
val String.toColor get() = Color(parseColor(this))

@Composable
private fun TabScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
fun Previewscreen() {
    val tabs = listOf(
        TabRowItem("Tab 1",
            Icons.Rounded.Place,
            Color.White,
            screen = { TabScreen(text = "Place") }),
        TabRowItem("Tab 2",
            Icons.Rounded.Search,
            Color.Green,
            screen = { TabScreen(text = "Search") }),
        TabRowItem("Tab 3",
            Icons.Rounded.Star,
            Color.Magenta,
            screen = { TabScreen(text = "Star") }),
    )
    NavigationBar(modifier = Modifier.background(Color.Green)) {
        tabs.forEach { tab ->
            NavigationBarItem(label = { Text(tab.title) },
                icon = { Icon(tab.icon, null) }, onClick = {}, selected = true,
                /*colors = NavigationBarItemDefaults.colors(
                    disabledIconColor = Color.Gray,
                    selectedIconColor = Color.LightGray,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.LightGray,
                    disabledTextColor = Color.Gray
                ),*/
                modifier = Modifier.background(Color.Yellow).padding(10.dp)
            )
        }
    }
}
