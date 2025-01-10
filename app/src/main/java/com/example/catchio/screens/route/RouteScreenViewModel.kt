package com.example.catchio.screens.route

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RouteScreenViewModel : ViewModel() {
    private val _rowsCount = MutableStateFlow(10)
    val rowsCount: StateFlow<Int> = _rowsCount
}
