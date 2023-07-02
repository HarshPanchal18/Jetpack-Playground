package com.example.first_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.first_jetcompose.ui.theme.FirstjetcomposeTheme
import dev.jeziellago.compose.markdowntext.MarkdownText

class MarkdownTextActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstjetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MarkdownTextLayout()
                }
            }
        }
    }
}

@Composable
fun MarkdownTextLayout() {
    val markdownText = "Link: https://github.com/HarshPanchal18\n" +
            "Phone: +1 234 567 890\"\n" +
            "# Header1\n" +
            "## Header2\n" +
            "* _`Markdown`_\n" +
            "* [Link](https://example.com)\n" +
            "![Image](https://example.com/img.png)\n" +
            "<a href=\"https://www.google.com/\">Google</a>\n\n" +
            "> Blockquote\n" +
            ">> Nested Blockquote\n" +
            "```javascript\n" +
            "var specificLanguage_code = \n" +
            "{\n" +
            "    \"data\": {\n" +
            "        \"lookedUpPlatform\": 1,\n" +
            "        \"query\": \"Kasabian+Test+Transmission\",\n" +
            "        \"lookedUpItem\": {\n" +
            "            \"name\": \"Test Transmission\",\n" +
            "            \"artist\": \"Kasabian\",\n" +
            "            \"album\": \"Kasabian\",\n" +
            "            \"picture\": null,\n" +
            "            \"link\": \"http://open.spotify.com/track/5jhJur5n4fasblLSCOcrTp\"\n" +
            "        }\n" +
            "    }\n" +
            "}\n" +
            "```\n"

    val context = LocalContext.current.applicationContext
    val rawMarkdown = String(context.resources.openRawResource(R.raw.markdown).readBytes()) +
            "\n# HTML SECTION ${String(context.resources.openRawResource(R.raw.html).readBytes())}"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MarkdownText(
            markdown = rawMarkdown,
            modifier = Modifier.padding(8.dp),
            fontSize = 20.sp,
        )
    }
}
