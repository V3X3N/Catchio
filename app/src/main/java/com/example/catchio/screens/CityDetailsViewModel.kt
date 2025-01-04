package com.example.catchio.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CityDetailsViewModel : ViewModel() {
    private val _cityDetails = MutableStateFlow("Details about the city")
    val cityDetails: StateFlow<String> = _cityDetails

    fun loadCityDetails(row: Int, column: Int) {
        _cityDetails.value = "City at row $row, column $column is a great place!"
    }
}