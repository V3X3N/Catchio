package com.example.catchio.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.catchio.R
import com.example.catchio.SharedPreferencesHelper
import com.example.catchio.dragon.Dragon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TownDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferencesHelper = SharedPreferencesHelper(application)

    private val _townLists = MutableStateFlow<List<List<Town>>>(List(20) { emptyList() })
    val townLists: StateFlow<List<List<Town>>> = _townLists

    private val _townDetails = MutableStateFlow("")
    val townDetails: StateFlow<String> = _townDetails

    private val _caughtDragons = MutableStateFlow(sharedPreferencesHelper.loadDragons())

    init {
        _townLists.value = listOf(
            listOf(
                Town.TownData(dragon = Dragon.create("Ferxe",1,5)),
            ),
            emptyList(), emptyList(),
            listOf(
                Town.TownData(dragon = Dragon.create("Ferxe",1,5)),
                Town.TownData(dragon = Dragon.create("Itu",1,5)),
                Town.TownData(imageResId = R.drawable.none)
            ),
            listOf(
                Town.TownData(dragon = Dragon.create("Ferxe",1,5)),
                Town.TownData(dragon = Dragon.create("Itu",1,5)),
                Town.TownData(imageResId = R.drawable.none)
            ),
            emptyList(), emptyList(),
            listOf(
                Town.TownData(dragon = Dragon.create("Ferxe",1,5)),
                Town.TownData(dragon = Dragon.create("Itu",1,5)),
                Town.TownData(imageResId = R.drawable.none)
            ),
            listOf(
                Town.TownData(dragon = Dragon.create("Ferxe",1,5)),
                Town.TownData(dragon = Dragon.create("Itu",1,5)),
                Town.TownData(imageResId = R.drawable.none)
            ),
            emptyList(), emptyList(),
            listOf(
                Town.TownData(dragon = Dragon.create("Ferxe",1,5)),
                Town.TownData(dragon = Dragon.create("Itu",1,5)),
                Town.TownData(imageResId = R.drawable.none)
            ),
            listOf(
                Town.TownData(dragon = Dragon.create("Ferxe",1,5)),
                Town.TownData(dragon = Dragon.create("Itu",1,5)),
                Town.TownData(imageResId = R.drawable.none)
            ),
            emptyList(), emptyList(),
            listOf(
                Town.TownData(dragon = Dragon.create("Ferxe",1,5)),
                Town.TownData(dragon = Dragon.create("Itu",1,5)),
                Town.TownData(imageResId = R.drawable.none)
            ),
            listOf(
                Town.TownData(dragon = Dragon.create("Ferxe",1,5)),
                Town.TownData(dragon = Dragon.create("Itu",1,5)),
                Town.TownData(imageResId = R.drawable.none)
            ),
            emptyList(), emptyList(),
            listOf(
                Town.TownData(dragon = Dragon.create("Tapree",1,5)),
                Town.TownData(dragon = Dragon.create("Miurfinn",1,5)),
                Town.TownData(imageResId = R.drawable.none)
            ),
        )
    }

    private val _generatedDragon = MutableStateFlow<Dragon?>(null)

    fun updateTownDetails(town: Town.TownData?, row: Int, column: Int) {
        _townDetails.value = when (town) {
            is Town.TownData -> {
                if (town.dragon != null) {
                    // Wykorzystanie poziomów zapisanych w Town.TownData
                    val generatedDragon = Dragon.create(town.dragon.name, town.dragon.level, town.dragon.level)
                    _generatedDragon.value = generatedDragon

                    "Town at row $row, column $column\n" +
                            "Dragon: ${generatedDragon.name} (Lv. ${generatedDragon.level})\n" +
                            "Type: ${generatedDragon.type}\n" +
                            "HP: ${generatedDragon.hp}\n" +
                            "Attack: ${generatedDragon.attack}\n" +
                            "Defense: ${generatedDragon.defense}\n" +
                            "Speed: ${generatedDragon.speed}\n" +
                            "Hidden Stats: HP Bonus: ${generatedDragon.hiddenStats.hpBonus}, " +
                            "Attack Bonus: ${generatedDragon.hiddenStats.attackBonus}, " +
                            "Defense Bonus: ${generatedDragon.hiddenStats.defenseBonus}, " +
                            "Speed Bonus: ${generatedDragon.hiddenStats.speedBonus}\n" +
                            "Attacks: ${generatedDragon.attacks}"
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