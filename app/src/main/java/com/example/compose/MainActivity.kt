package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            Greeting("Android")
//            MainScreen()
//            ExtractComposableHorizontally()
//            ExtractComposableVertically()
            CombinedRowColumn()
        }
    }
}

/** Modifier --> the order of the modifiers matter, if we change it we will get a different output in terms of UI
 *  EXAMPLE -> if we change the order of clickable and padding from Greetings, the clickable surface will be different.
 *  right now it is on the entire width of the composable, if we declare the padding before clickable, it will only be clickable inside the composable excluding the padding. */
@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!",
        modifier = Modifier
            .size(200.dp, 240.dp)
            .clickable(onClick = {})
            .padding(all = 24.dp)
    )
}

/** In order to use a Surface(it is like a View), we need to at least specify the space will take this composable, fillMaxSize() in this case*/
@Composable
fun MainScreen() {
    Surface(color = Color.DarkGray, modifier = Modifier.fillMaxSize()) {
        /** composable with wrap content attribute */
        Surface(
            color = Color.Magenta,
            modifier = Modifier.wrapContentSize(align = Alignment.Center)
        ) {
            Text(
                text = "Wrapped content",
                /** no need for this now that the surface has it as modifier and it will wrap around the Text within it. */
                // modifier = Modifier.wrapContentSize(),
                style = MaterialTheme.typography.h3
            )
        }
    }
}

/** Extract composable to reuse it in structure like Row or Column */
@Composable
fun ExtractComposableHorizontally() {
    Surface(color = Color.DarkGray, modifier = Modifier.fillMaxSize()) {
        /** Row -> horizontal arrangement
        It is important to specify the horizontal parameters with a Row (horizontal arrangement) */
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RowColoredBar(Color.Red)
            RowColoredBar(Color.Magenta)
            RowColoredBar(Color.Cyan)
            RowColoredBar(Color.Yellow)
            RowColoredBar(Color.Blue)
        }
    }
}

@Composable
fun ExtractComposableVertically() {
    Surface(color = Color.DarkGray, modifier = Modifier.fillMaxSize()) {
        /** Column -> vertical arrangement
        It is important to specify the vertical parameters with a Column (vertical arrangement) */
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ColumnColoredBar(Color.Red)
            ColumnColoredBar(Color.Magenta)
            ColumnColoredBar(Color.Cyan)
            ColumnColoredBar(Color.Yellow)
            ColumnColoredBar(Color.Blue)
        }
    }
}

@Composable
fun CombinedRowColumn() {
    Surface(color = Color.DarkGray, modifier = Modifier.fillMaxSize()) {
        /** We can build everything, we can nest everything as a composable inside a parent composable */
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ColoredSquare(Color.Red)
                ColoredSquare(Color.Magenta)
            }
            ColoredSquare(Color.Cyan)
            ColoredSquare(Color.Yellow)
            ColoredSquare(Color.Blue)
        }
    }
}

@Composable
fun RowColoredBar(color: Color) {
    Surface(color = color, modifier = Modifier.size(60.dp, 600.dp)) {}
}

@Composable
fun ColumnColoredBar(color: Color) {
    Surface(color = color, modifier = Modifier.size(350.dp, 100.dp)) {}
}

@Composable
fun ColoredSquare(color: Color) {
    Surface(color = color, modifier = Modifier.size(100.dp, 100.dp)) {}
}

/** Preview to show the composable before running the app*/
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    /** delete the below function to have the same logic of the class */

    //    SimpleTextComposeTheme {}
    //    Greeting("Android")
    //    MainScreen()
    //    ExtractComposableHorizontally()
    //    ExtractComposableVertically()
        CombinedRowColumn()
}

