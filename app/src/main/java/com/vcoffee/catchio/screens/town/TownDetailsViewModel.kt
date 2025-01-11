package com.vcoffee.catchio.screens.town

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.vcoffee.catchio.R
import com.vcoffee.catchio.dragon.Dragon
import com.vcoffee.catchio.dragon.DragonRepository
import com.vcoffee.catchio.dragon.FireStoreDragon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TownDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val dragonRepository = DragonRepository(firebaseFirestore)

    private val _townLists = MutableStateFlow<List<List<Town.TownData>>>(List(20) { emptyList() })
    val townLists: StateFlow<List<List<Town.TownData>>> = _townLists

    private val _townDetails = MutableStateFlow("")
    val townDetails: StateFlow<String> = _townDetails

    private val _generatedDragon = MutableStateFlow<Dragon?>(null)

    init {
        _townLists.value = listOf(
            listOf(Town.TownData(dragonName = "Ferxe", levelRange = 1..5)),
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
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            _generatedDragon.value?.let { caughtDragon ->
                viewModelScope.launch {
                    try {
                        val firestoreDragon = caughtDragon.toFireStoreDragon()
                        val dragonRef = dragonRepository.addDragon(firestoreDragon)

                        if (dragonRef != null) {
                            var stable = dragonRepository.getDragonStable(userId)
                            var stableId: String? = null

                            if (stable == null) {
                                stableId = dragonRepository.createDragonStable(userId)
                                if (stableId != null) {
                                    stable = dragonRepository.getDragonStable(userId)
                                } else {
                                    Log.e("TownDetailsViewModel", "Nie udało się utworzyć stajni")
                                }
                            } else {
                                val querySnapshot = firebaseFirestore.collection("dragonStable")
                                    .whereEqualTo("userRef", firebaseFirestore.collection("users").document(userId))
                                    .get()
                                    .await()

                                if (!querySnapshot.isEmpty) {
                                    stableId = querySnapshot.documents[0].id
                                } else {
                                    Log.e("TownDetailsViewModel", "Nie znaleziono stajni mimo wcześniejszego sprawdzenia")
                                }
                            }

                            if (stable != null && stableId != null) {
                                dragonRepository.updateDragonStable(stableId, dragonRef)
                                _generatedDragon.value = null
                            } else {
                                Log.e("TownDetailsViewModel", "Nie udało się pobrać/utworzyć stajni")
                            }
                        } else {
                            Log.e("TownDetailsViewModel", "Błąd zapisu smoka do Firestore")
                        }
                    } catch (e: Exception) {
                        Log.e("TownDetailsViewModel", "Błąd w catchDragon: ${e.message}")
                    }
                }
            }
        } else {
            Log.w("TownDetailsViewModel", "Użytkownik niezalogowany, nie można złapać smoka.")
        }
    }

    private fun Dragon.toFireStoreDragon(): FireStoreDragon {
        return FireStoreDragon(
            name = name,
            lvl = level,
            type = type,
            HS_HP = hiddenStats.hiddenHP,
            HS_Atk = hiddenStats.hiddenATK,
            HS_Def = hiddenStats.hiddenDEF,
            HS_Spd = hiddenStats.hiddenSPD,
            MoveSet = listOf(attacks),
            baseHP = Dragon.dragonData[name]!!.baseHP,
            baseATK = Dragon.dragonData[name]!!.baseATK,
            baseDEF = Dragon.dragonData[name]!!.baseDEF,
            baseSPD = Dragon.dragonData[name]!!.baseSPD
        )
    }
}