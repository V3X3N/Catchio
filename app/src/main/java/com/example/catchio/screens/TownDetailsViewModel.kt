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
    val caughtDragons: StateFlow<List<Dragon>> = _caughtDragons

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
                Town.TownData(imageResId = R.drawable.none)
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

    fun updateTownDetails(town: Town.TownData?, row: Int, column: Int) {
        _townDetails.value = when (town) {
            is Town.TownData -> {
                if (town.dragon != null) {
                    "Town at row $row, column $column\n" +
                            "Dragon: ${town.dragon.name} (Lv. ${town.dragon.level})\n" +
                            "Type: ${town.dragon.type}\n" +
                            "HP: ${town.dragon.hp}\n" +
                            "Attack: ${town.dragon.attack}\n" +
                            "Defense: ${town.dragon.defense}\n" +
                            "Speed: ${town.dragon.speed}\n" +
                            "Attacks: ${town.dragon.attacks}"
                } else if (town.imageResId != null) {
                    "Town at row $row, column $column\nImage: ${town.imageResId}"
                } else {
                    "Empty Town"
                }
            }
            else -> "Empty Town"
        }
    }

    fun catchDragon(dragon: Dragon?) {
        dragon?.let {
            val updatedDragons = _caughtDragons.value + it
            _caughtDragons.value = updatedDragons
            sharedPreferencesHelper.saveDragons(updatedDragons)
        }
    }
}
