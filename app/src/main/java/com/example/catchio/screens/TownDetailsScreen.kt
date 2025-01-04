package com.example.catchio.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.random.Random

@Composable
fun TownDetailsScreen(row: Int, column: Int, townDetailsViewModel: TownDetailsViewModel = viewModel()) {
    val townDetails by townDetailsViewModel.townDetails.collectAsState()
    val townLists by townDetailsViewModel.townLists.collectAsState()

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
    val textColor = if (backgroundColor == Color.Black) Color.White else Color.Black

    townDetailsViewModel.loadTownDetails(row, column)

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
            Text("Town Details", color = textColor)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Row: $row", color = textColor)
            Text("Column: $column", color = textColor)
            Spacer(modifier = Modifier.height(16.dp))
            Text(townDetails, color = textColor)
            Spacer(modifier = Modifier.height(16.dp))

            if (townDetails.contains("Image:")) {
                val imageResId = townDetails.substringAfter("Image:").trim().toIntOrNull()
                if (imageResId != null) {
                    DisplayImage(imageResId = imageResId)
                }
            } else if (townDetails.contains("Dragon:")) {
                val dragonName = townDetails.substringAfter("Dragon:").substringBefore("(").trim()
                val listIndex = row * 2 + column
                val currentTownList = townLists.getOrNull(listIndex) ?: emptyList()
                val currentTownData = currentTownList.find { (it as? Town.TownData)?.dragon?.name == dragonName } as? Town.TownData
                val dragonImageResId = currentTownData?.dragon?.imageResId

                if (dragonImageResId != null) {
                    DisplayImage(imageResId = dragonImageResId)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val listIndex = row * 2 + column
            val currentTownList = townLists.getOrNull(listIndex) ?: emptyList()

            Button(onClick = {
                if (currentTownList.isNotEmpty()) {
                    currentTownList.random(Random(System.currentTimeMillis()))
                    townDetailsViewModel.loadTownDetails(row, column)
                    println("Random Choice")
                }
            }) {
                Text("Random choice")
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

@Composable
fun DisplayImage(imageResId: Int) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.LightGray, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Town Image",
            modifier = Modifier.fillMaxSize()
        )
    }
}