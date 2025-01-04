package com.example.catchio.screens

import androidx.lifecycle.ViewModel
import com.example.catchio.R
import com.example.catchio.dragon.Dragon
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
                Town.TownData(dragon = Dragon.createItu()),
                Town.TownData(dragon = Dragon.createFerxe()),
                Town.TownData(imageResId = R.drawable.none)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownData(dragon = Dragon.createSoeshi()),
                Town.TownData(dragon = Dragon.createTapree()),
                Town.TownData(imageResId = R.drawable.none)
            ),
            listOf(
                Town.TownData(dragon = Dragon.createItu()),
                Town.TownData(dragon = Dragon.createSoeshi()),
                Town.TownData(imageResId = R.drawable.none),
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownData(dragon = Dragon.createSoeshi()),
                Town.TownData(dragon = Dragon.createFerxe()),
                Town.TownData(imageResId = R.drawable.none)
            ),
            listOf(
                Town.TownData(dragon = Dragon.createTapree()),
                Town.TownData(dragon = Dragon.createMiurfinn()),
                Town.TownData(imageResId = R.drawable.none)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownData(dragon = Dragon.createFerxe()),
                Town.TownData(dragon = Dragon.createTapree()),
                Town.TownData(imageResId = R.drawable.none)
            ),
            listOf(
                Town.TownData(dragon = Dragon.createTapree()),
                Town.TownData(dragon = Dragon.createItu()),
                Town.TownData(imageResId = R.drawable.none)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownData(dragon = Dragon.createSoeshi()),
                Town.TownData(dragon = Dragon.createTapree()),
                Town.TownData(imageResId = R.drawable.none)
            ),
            listOf(
                Town.TownData(dragon = Dragon.createItu()),
                Town.TownData(dragon = Dragon.createFerxe()),
                Town.TownData(imageResId = R.drawable.none)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownData(dragon = Dragon.createTapree()),
                Town.TownData(dragon = Dragon.createItu()),
                Town.TownData(imageResId = R.drawable.none)
            )
        )
    }

    fun loadTownDetails(row: Int, column: Int) {
        val listIndex = row * 2 + column
        val currentTownList = _townLists.value.getOrNull(listIndex) ?: emptyList()

        if (currentTownList.isNotEmpty()) {
            val randomTown = currentTownList.random()
            _townDetails.value = when (randomTown) {
                is Town.TownData -> {
                    if (randomTown.dragon != null) {
                        "Town at row $row, column $column\n" +
                                "Dragon: ${randomTown.dragon.name} (Lv. ${randomTown.dragon.level})\n" +
                                "Type: ${randomTown.dragon.type}\n" +
                                "HP: ${randomTown.dragon.hp}\n" +
                                "Attack: ${randomTown.dragon.attack}\n" +
                                "Defense: ${randomTown.dragon.defense}\n" +
                                "Speed: ${randomTown.dragon.speed}\n" +
                                "Attacks: ${randomTown.dragon.attacks}"
                    } else if (randomTown.imageResId != null) {
                        "Town at row $row, column $column\n" +
                                "Image: ${randomTown.imageResId}"
                    } else {
                        "Empty Town"
                    }
                }
            }
        }
    }
}