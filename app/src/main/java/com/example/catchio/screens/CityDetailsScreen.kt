package com.example.catchio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
        0 -> Color.Yellow
        3 -> Color.Blue
        4 -> Color.Red
        7 -> Color.Green
        8 -> Color.Cyan
        11 -> Color.Magenta
        12 -> Color.Gray
        15 -> Color.LightGray
        16 -> Color.White
        19 -> Color.Black
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

    var displayNumber by remember { mutableStateOf<Int?>(null) }

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

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Gray, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayNumber?.toString() ?: "No Number",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val randomValue = randomRange.random()
                displayNumber = randomValue
            }) {
                Text("Generate Number")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                println("Catch!!")
            }) {
                Text("Catch!!")
            }
        }
    }
}
