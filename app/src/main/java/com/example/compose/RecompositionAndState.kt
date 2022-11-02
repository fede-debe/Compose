package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/** From List to ArrayList to be able to add element to it */
val nameList: ArrayList<String> = arrayListOf("Federico", "Mar", "Coco", "Janin", "Pichirin")

class CompositionAndState : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            ListOfNames(names = nameList)
            UiScreen()
        }
    }
}

/** RECOMPOSITION -> allows the whole UI to be rebuilt and the state to be changed
Important --> Recomposition depends on the State to be triggered
We can have as many elements as we want */
@Composable
fun ListOfNames(names: List<String>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (name in names) {
            ListName(name = name)
        }

        Button(onClick = { nameList.add("New name") }) {
            Text(
                text = "Add new name",
                style = MaterialTheme.typography.h4
            )
        }
    }
}

@Composable
fun ListName(name: String) {
    Text(text = "Hello $name!")
}

/** STATE --> It is any value that can change over time
 *  Application UI flow: Event -> Update State -> Display State
 *  Advantages: - Testability
 *              - State encapsulation
 *              - UI consistency */

/** We are going to use the main composable to handle all the State update. Instead of having a
 *  state in each composable. We need to have one parent that arranges this process and decide
 *  when the state change */
@Composable
fun UiScreen() {
    /** State lifted from composable to higher hierarchy composable */
    val listState = remember {
        mutableStateListOf("John", "Amanda")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StateList(listState) { listState.add("Frank") }
    }
}

/** In order to have a State into a composable, we need to define a State for this composable */
@Composable
fun StateList(nameList: List<String>, onClick: () -> Unit) {
    /** A state object that contains a list of element, we need to add a REMEMBER block to allow that
     *  the state will be remember over Recomposition. This is not the best practice while using state,
     *  should be placed within the composable with higher hierarchy ("UiScreen()" in this case) to
     *  handle all the states (entry point for state update) */
//    val listState = remember {
//        mutableStateListOf("John", "Amanda")
//    }
//    for (name in listState) {
//        ListName(name = name)
//    }
//    Button(onClick = { listState.add("Frank") }) {
//        Text(
//            text = "Add new name",
//            style = MaterialTheme.typography.h5
//        )
//    }
    for (name in nameList) {
        ListName(name = name)
    }
    Button(onClick = { onClick }) {
        Text(
            text = "Add new name",
            style = MaterialTheme.typography.h5
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewRecomposition() {
//    ListOfNames(names = nameList)
    UiScreen()
}