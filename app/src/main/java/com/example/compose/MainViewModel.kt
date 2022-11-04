package com.example.compose

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel()  {
    // STATE
    val textFieldState = MutableLiveData("")

    fun onTextChanged(newText: String) {
        textFieldState.value = newText
    }
}