package com.example.catchio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CityDetailsScreen(row: Int, column: Int, cityDetailsViewModel: CityDetailsViewModel = viewModel()) {
    val cityDetails by cityDetailsViewModel.cityDetails.collectAsState()
    cityDetailsViewModel.loadCityDetails(row, column)

    val backgroundColor = when (row * 2 + column) {
        0 -> Color.Red
        3 -> Color.Yellow
        4 -> Color.Blue
        7 -> Color.Cyan
        8 -> Color.Green
        11 -> Color.Black
        12 -> Color.DarkGray
        15 -> Color.Gray
        16 -> Color.LightGray
        19 -> Color.Magenta
        else -> Color.Transparent
    }

    val randomRange = when (row * 2 + column) {
        0 -> 1..6
        3 -> 7..12
        4 -> 13..18
        7 -> 19..24
        8 -> 25..30
        11 -> 31..36
        12 -> 37..42
        15 -> 43..48
        16 -> 49..54
        19 -> 55..60
        else -> 0..0
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("City Details", color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Row: $row", color = Color.White)
            Text("Column: $column", color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Text(cityDetails, color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val randomValue = randomRange.random()
                println("Button clicked! Random value: $randomValue")
            }) {
                Text("Click Me")
            }
        }
    }
}