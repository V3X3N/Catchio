package com.example.catchio.screens

import androidx.lifecycle.ViewModel

class Screen2ViewModel : ViewModel() {
    val squares: List<String> = List(24) { "Square ${it + 1}" }
}