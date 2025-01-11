package com.vcoffee.catchio.screens.backpack

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.vcoffee.catchio.dragon.Dragon
import com.vcoffee.catchio.dragon.DragonDetailsDialog
import com.vcoffee.catchio.screens.town.DisplayImage

@Composable
fun BackpackScreen(backpackScreenViewModel: BackpackScreenViewModel = viewModel()) {
    val caughtDragons by backpackScreenViewModel.caughtDragons.collectAsState()

    val totalSlots = 40

    val dragonSlots = (caughtDragons + List(totalSlots - caughtDragons.size) { null })
        .take(totalSlots)

    var selectedDragon by remember { mutableStateOf<Dragon?>(null) }

    if (selectedDragon != null) {
        DragonDetailsDialog(dragon = selectedDragon!!, onDismiss = { selectedDragon = null })
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(dragonSlots) { dragon ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(
                        color = if (dragon != null) Color.Gray else Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(4.dp)
                    .clickable {
                        if (dragon != null) {
                            selectedDragon = dragon
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (dragon != null) {
                    DisplayImage(imageResId = dragon.imageResId)
                }
            }
        }
    }
}