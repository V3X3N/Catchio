package com.example.catchio.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CityDetailsScreen(row: Int, column: Int, cityDetailsViewModel: CityDetailsViewModel = viewModel()) {
    val cityDetails by cityDetailsViewModel.cityDetails.collectAsState()
    cityDetailsViewModel.loadCityDetails(row, column)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("City Details")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Row: $row")
        Text("Column: $column")
        Spacer(modifier = Modifier.height(16.dp))
        Text(cityDetails)
    }
}