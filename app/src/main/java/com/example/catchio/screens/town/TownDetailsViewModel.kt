package com.example.catchio.screens.town

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.catchio.R
import com.example.catchio.SharedPreferencesHelper
import com.example.catchio.dragon.Dragon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TownDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferencesHelper = SharedPreferencesHelper(application)

    private val _townLists = MutableStateFlow<List<List<Town.TownData>>>(List(20) { emptyList() })
    val townLists: StateFlow<List<List<Town.TownData>>> = _townLists

    private val _townDetails = MutableStateFlow("")
    val townDetails: StateFlow<String> = _townDetails

    private val _caughtDragons = MutableStateFlow(sharedPreferencesHelper.loadDragons())

    private val _generatedDragon = MutableStateFlow<Dragon?>(null)

    init {
        _townLists.value = listOf(
            listOf(
                Town.TownData(dragonName = "Ferxe", levelRange = 1..5),
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownData(dragonName = "Ferxe", levelRange = 5..10),
                Town.TownData(dragonName = "Itu", levelRange = 5..10),
                Town.TownData(imageResId = R.drawable.none)
            ),
            listOf(
                Town.TownData(dragonName = "Ferxe", levelRange = 1..5),
                Town.TownData(dragonName = "Itu", levelRange = 1..5),
                Town.TownData(imageResId = R.drawable.none)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownData(dragonName = "Ferxe", levelRange = 1..5),
                Town.TownData(dragonName = "Itu", levelRange = 1..5),
                Town.TownData(imageResId = R.drawable.none)
            ),
            listOf(
                Town.TownData(dragonName = "Ferxe", levelRange = 1..5),
                Town.TownData(dragonName = "Itu", levelRange = 1..5),
                Town.TownData(imageResId = R.drawable.none)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownData(dragonName = "Ferxe", levelRange = 1..5),
                Town.TownData(dragonName = "Itu", levelRange = 1..5),
                Town.TownData(imageResId = R.drawable.none)
            ),
            listOf(
                Town.TownData(dragonName = "Ferxe", levelRange = 1..5),
                Town.TownData(dragonName = "Itu", levelRange = 1..5),
                Town.TownData(imageResId = R.drawable.none)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownData(dragonName = "Ferxe", levelRange = 1..5),
                Town.TownData(dragonName = "Itu", levelRange = 1..5),
                Town.TownData(imageResId = R.drawable.none)
            ),
            listOf(
                Town.TownData(dragonName = "Ferxe", levelRange = 1..5),
                Town.TownData(dragonName = "Itu", levelRange = 1..5),
                Town.TownData(imageResId = R.drawable.none)
            ),
            emptyList(),
            emptyList(),
            listOf(
                Town.TownData(dragonName = "Tapree", levelRange = 1..5),
                Town.TownData(dragonName = "Miurfinn", levelRange = 1..5),
                Town.TownData(imageResId = R.drawable.none)
            )
        )
    }

    fun updateTownDetails(town: Town.TownData?, row: Int, column: Int) {
        _townDetails.value = when (town) {
            is Town.TownData -> {
                val dragon = town.dragonName?.let {
                    val range = town.levelRange ?: 1..1
                    Dragon.create(it, range.first, range.last)
                }
                if (dragon != null) {
                    _generatedDragon.value = dragon
                    "Town at row $row, column $column\n" +
                            "Dragon: ${dragon.name} (Lv. ${dragon.level})\n" +
                            "Type: ${dragon.type}\n" +
                            "HP: ${dragon.hp}\n" +
                            "Attack: ${dragon.attack}\n" +
                            "Defense: ${dragon.defense}\n" +
                            "Speed: ${dragon.speed}\n" +
                            "Hidden Stats: HP Bonus: ${dragon.hiddenStats.hiddenHP}, " +
                            "Attack Bonus: ${dragon.hiddenStats.hiddenATK}, " +
                            "Defense Bonus: ${dragon.hiddenStats.hiddenDEF}, " +
                            "Speed Bonus: ${dragon.hiddenStats.hiddenSPD}\n" +
                            "Attacks: ${dragon.attacks}"
                } else if (town.imageResId != null) {
                    "Town at row $row, column $column\nImage: ${town.imageResId}"
                } else {
                    "Empty Town"
                }
            }
            else -> "Empty Town"
        }
    }

    fun catchDragon() {
        _generatedDragon.value?.let { caughtDragon ->
            val updatedDragons = _caughtDragons.value + caughtDragon
            _caughtDragons.value = updatedDragons
            sharedPreferencesHelper.saveDragons(updatedDragons)
            _generatedDragon.value = null
        }
    }
}