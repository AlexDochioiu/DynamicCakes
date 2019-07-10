package com.github.alexdochioiu.about_ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Made By Alex Dochioiu"
    }
    val text: LiveData<String> = _text
}