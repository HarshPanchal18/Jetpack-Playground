package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme

class StickyHeaderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                val sectionedItem = ArrayList<SectionListItem>()
                for (section in 1..5) {
                    val itemList = ArrayList<Item>()
                    for (item in 1..3) {
                        itemList.add(Item("$section.$item", "$item description"))
                    }
                    sectionedItem.add(SectionListItem("Section: $section", itemList))
                }

                SectionedStickyRecyclerView(
                    items = sectionedItem,
                    modifier = Modifier.fillMaxSize().background(Color.LightGray)
                )
            }
        }
    }
}

data class SectionListItem(
    val selectionTitle: String,
    val items: List<Item>
)

data class Item(
    val name: String,
    val description: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SectionedStickyRecyclerView(
    items: List<SectionListItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items.forEachIndexed { _, sectionedItem ->
            stickyHeader {
                Card(
                    modifier = modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Text(
                            text = sectionedItem.selectionTitle,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            style = TextStyle(color = Color.Black, fontSize = 16.sp)
                        )
                    }
                }
            }
            items(sectionedItem.items) { item ->
                ImageCard(
                    painter = painterResource(R.drawable.dog2),
                    contentDescription = null,
                    title = "Section ${item.name}"
                )
            }
        }
    }
}

@Composable
fun ImageCard(painter: Painter, contentDescription: String? = null, title: String) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(percent = 10))
            .background(Color.White)
    ) {
        Image(
            painter, contentDescription, contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = title,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart),
            color = Color.White,
            fontSize = 20.sp
        )
    }
}
