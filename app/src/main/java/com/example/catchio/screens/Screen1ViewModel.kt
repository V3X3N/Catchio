package com.example.catchio.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Screen1ViewModel : ViewModel() {
    private val _state = MutableStateFlow("Screen 1 Content")
    val state: StateFlow<String> = _state
}