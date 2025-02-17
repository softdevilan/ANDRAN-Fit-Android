package com.example.andranfitmobile.ui.workouts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrainersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is trainers Fragment"
    }
    val text: LiveData<String> = _text
}