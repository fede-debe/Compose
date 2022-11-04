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
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/** From List to ArrayList to be able to add element to it */
val nameList: ArrayList<String> = arrayListOf("Federico", "Mar", "Coco", "Janin", "Pichirin")

class RecompositionAndState : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            ListOfNames(names = nameList)
//            UiScreen()
            UiScreenViewModel()
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

/** In order to have a State into a composable, we need to define a State for this composable */
@Composable
fun StateList(
    nameList: List<String>,
    buttonClick: () -> Unit,
    textFieldValue: String,
    textFieldUpdate: (newName: String) -> Unit
) {
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

//        (empty string as initial value for the TextField)
//        val  nameStateContent = remember {
//             mutableStateOf("")
//        }

    /** TextField --> similar to an EditText, it has a value as param (input), and a listener.
     *  For the value we can pass an empty string for now but we need to set up a state, if not
     *  the input value will not be taken as parameter and it will print the empty string we set up
     *  as default value . In order to use the dynamic name we need to lift up the state of the
     *  TextField */


    // by using ".value" we make sure that every time the state value changes, the field will be updated
//    TextField(value = nameStateContent.value, onValueChange = {
//        // in this way we override the old value in the state with the new input as callback
//            newInput -> nameStateContent.value = newInput
//    })

    TextField(value = textFieldValue, onValueChange = textFieldUpdate)
    // in this way we override the old value in the state with the new input as callback
    // we need an inout parameter to handle the callback for the TexField changes.

    for (name in nameList) {
        ListName(name = name)
    }
    Button(onClick = buttonClick) {
        Text(
            text = "Add new name",
            style = MaterialTheme.typography.h5
        )
    }
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
    val nameList = remember {
        mutableStateListOf("Federico", "Mar")
    }
    val listState = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /** 1st param --> StateList
         * 2nd param --> Action
         * 3rd param --> Value for the TextField
         * 4th param --> Actual update for the TextField
         * */
        StateList(nameList,
            { nameList.add(listState.value) },
            listState.value,
            { newName -> listState.value = newName })
    }
}

/** State lifted to the ViewModel */
@Composable
fun UiScreenViewModel(viewModel: MainViewModel = MainViewModel()) {
    // we need to convert this object to a state object --> observeAsState with empty string as default value in case it isn't initialized
    val listState = viewModel.textFieldState.observeAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StateListViewModel(
            listState.value ?: ""
        ) { newName ->
            if (newName.isNotEmpty()) {
                viewModel.onTextChanged(newName)
            }
        }
        // we need update the state inside the ViewModel { newName -> listState.value = newName }
    }
}

@Composable
fun StateListViewModel(
    textFieldValue: String,
    textFieldUpdate: (newName: String) -> Unit
) {
    TextField(value = textFieldValue, onValueChange = textFieldUpdate)
    Button(onClick = { }) {
        Text(text = textFieldValue)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewRecomposition() {
//    ListOfNames(names = nameList)
//    UiScreen()
    UiScreenViewModel()
}