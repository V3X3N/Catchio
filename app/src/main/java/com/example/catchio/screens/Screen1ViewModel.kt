package com.example.catchio.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Screen1ViewModel : ViewModel() {
    private val _rowsCount = MutableStateFlow(10)
    val rowsCount: StateFlow<Int> = _rowsCount
}