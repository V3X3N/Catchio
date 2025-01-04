package com.example.catchio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
        0 -> Color.Yellow
        1 -> Color.Blue
        2 -> Color.Red
        3 -> Color.Green
        4 -> Color.Cyan
        5 -> Color.Magenta
        6 -> Color.Gray
        7 -> Color.LightGray
        8 -> Color.White
        9 -> Color.Black
        else -> Color.Transparent
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
        }
    }
}