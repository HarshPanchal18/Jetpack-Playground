package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import java.util.Locale

class SearchCountryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentColor = Color.Cyan
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) { Navigation() }
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController = navController)
        }
        composable(
            "details/{countryName}",
            arguments = listOf(navArgument("countryName") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("countryName")?.let { countryName ->
                DetailScreen(countryName = countryName)
            }
        }
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value, onValueChange = { state.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(percent = 20)),
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = null,
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp),
                tint = Color.Black
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(onClick = { state.value = TextFieldValue("") }) {
                    Icon(
                        imageVector = Icons.Default.Close, contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        },
        label = { Text("Search Country") },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )
    )
}

@Composable
fun CountryListItem(countryText: String, onItemClick: (country: String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(percent = 20))
            .background(Color.Green.copy(0.5F))
            .height(57.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .clickable(onClick = { onItemClick(countryText) }),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = countryText, fontSize = 18.sp, color = Color.Black)
    }
}

@Composable
fun CountryListWithEmoji(navController: NavController, state: MutableState<TextFieldValue>) {
    val countries = getListOfCountries()
    var filteredCountries: ArrayList<String>

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        val searchedText = state.value.text
        filteredCountries = if (searchedText.isEmpty()) countries
        else {
            val resultList = ArrayList<String>()
            for (country in countries) {
                if (country.lowercase().contains(searchedText.lowercase()))
                    resultList.add(country)
            }
            resultList
        }
        items(filteredCountries) { filteredCountry ->
            CountryListItem(
                countryText = filteredCountry,
                onItemClick = { selectedCountry ->
                    navController.navigate("details/$selectedCountry") {

                        // Popup to the start destination of graph to avoid building up a large stack of destination on the backstack
                        // as users select items
                        popUpTo("main") { saveState = true }

                        launchSingleTop = true // Avoid multiple copies of the same destination when re-selecting the same item
                        restoreState = true // Restore when re-selecting prev selected item
                    }
                }
            )
        }
    }
}

fun getListOfCountries(): ArrayList<String> {
    val isoCountryCodes = Locale.getISOCountries() // gets list of all ISO country codes
    val countryWithEmoji = ArrayList<String>()

    for (countryCode in isoCountryCodes) {
        val locale = Locale("", countryCode)
        val countryName = locale.displayCountry // country name from through the current country-code
        val flagOffset = 0x1F1E6
        val asciiOffset = 0x41

        // Get the first and second characters of the country code
        val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
        val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset

        // create flag emoji for the country
        val flag = (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))

        countryWithEmoji.add("$flag   $countryName")
    }

    return countryWithEmoji
}

@Composable
fun MainScreen(navController: NavController) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Column {
        SearchView(state = textState)
        CountryListWithEmoji(navController = navController, state = textState)
    }
}

@Composable
fun DetailScreen(countryName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow.copy(0.6F))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = countryName,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 22.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Previews() {
    Column {
        CountryListItem(countryText = "India", onItemClick = {})
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        SearchView(state = textState)
    }
}
