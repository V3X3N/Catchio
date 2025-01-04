package com.example.catchio.screens

import androidx.lifecycle.ViewModel
import com.example.catchio.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TownDetailsViewModel : ViewModel() {
    private val _townLists = MutableStateFlow<List<List<Town>>>(List(20) { emptyList() })
    val townLists: StateFlow<List<List<Town>>> = _townLists

    private val _townDetails = MutableStateFlow("")
    val townDetails: StateFlow<String> = _townDetails

    init {
        _townLists.value = listOf(
            listOf(
                Town.TownImage(R.drawable.ferxe),
                Town.TownImage(R.drawable.itu),
                Town.TownImage(R.drawable.none)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownImage(R.drawable.miurfinn),
                Town.TownImage(R.drawable.soeshi),
                Town.TownImage(R.drawable.none)
            ),
            listOf(
                Town.TownImage(R.drawable.tapree),
                Town.TownImage(R.drawable.none),
                Town.TownImage(R.drawable.ferxe)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownImage(R.drawable.itu),
                Town.TownImage(R.drawable.miurfinn),
                Town.TownImage(R.drawable.none)
            ),
            listOf(
                Town.TownImage(R.drawable.soeshi),
                Town.TownImage(R.drawable.tapree),
                Town.TownImage(R.drawable.none)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownImage(R.drawable.ferxe),
                Town.TownImage(R.drawable.itu),
                Town.TownImage(R.drawable.miurfinn)
            ),
            listOf(
                Town.TownImage(R.drawable.soeshi),
                Town.TownImage(R.drawable.tapree),
                Town.TownImage(R.drawable.ferxe)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownImage(R.drawable.itu),
                Town.TownImage(R.drawable.miurfinn),
                Town.TownImage(R.drawable.soeshi)
            ),
            listOf(
                Town.TownImage(R.drawable.tapree),
                Town.TownImage(R.drawable.ferxe),
                Town.TownImage(R.drawable.itu)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownImage(R.drawable.miurfinn),
                Town.TownImage(R.drawable.soeshi),
                Town.TownImage(R.drawable.tapree)
            )
        )
    }

    fun loadTownDetails(row: Int, column: Int) {
        _townDetails.value = "Town at row $row, column $column"
    }
}
