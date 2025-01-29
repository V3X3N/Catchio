package com.vcoffee.catchio.screens.backpack

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

class BackpackScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val dragonRepository = DragonRepository(firebaseFirestore)
    private val _caughtDragons = MutableStateFlow<List<Dragon>>(emptyList())
    private val _battleDragons = MutableStateFlow<List<Dragon>>(emptyList())

    val caughtDragons: StateFlow<List<Dragon>> = _caughtDragons
    val battleDragons: StateFlow<List<Dragon>> = _battleDragons

    init {
        viewModelScope.launch {
            Firebase.auth.currentUser?.let { user ->
                loadDragons(user.uid)
            } ?: run {
                Log.e("BackpackVM", "Brak zalogowanego użytkownika")
                _caughtDragons.value = emptyList()
                _battleDragons.value = emptyList()
            }
        }
    }

    private suspend fun loadDragons(userId: String) {
        try {
            val stable = dragonRepository.getDragonStable(userId)
            if (stable == null) {
                Log.e("BackpackVM", "Brak stajni dla użytkownika")
                return
            }

            Log.d("BackpackVM", """
                |Dane ze stajni:
                |Zwykłe smoki: ${stable.dragons.size}
                |Battle smoki: ${stable.battleDragons.size}
            """.trimMargin())

            _caughtDragons.value = if (stable.dragons.isNotEmpty()) {
                val dragons = dragonRepository.getDragonsFromStable(stable.dragons)
                Log.d("BackpackVM", "Załadowano ${dragons.size} zwykłych smoków: ${dragons.map { it.name }}")
                dragons.map { it.toDragon() }
            } else {
                emptyList()
            }

            _battleDragons.value = if (stable.battleDragons.isNotEmpty()) {
                val battleDragons = dragonRepository.getDragonsFromStable(stable.battleDragons)
                Log.d("BackpackVM", "Załadowano ${battleDragons.size} battle smoków: ${battleDragons.map { it.name }}")
                battleDragons.map { it.toDragon() }
            } else {
                emptyList()
            }

        } catch (e: Exception) {
            Log.e("BackpackVM", "Błąd ładowania smoków: ${e.message}")
        }
    }

    private fun FireStoreDragon.toDragon(): Dragon {
        val imageResId = when (name) {
            "Ferxe" -> R.drawable.ferxe
            "Itu" -> R.drawable.itu
            "Tapree" -> R.drawable.tapree
            "Soeshi" -> R.drawable.soeshi
            "Miurfinn" -> R.drawable.miurfinn
            else -> R.drawable.ic_launcher_background
        }
        return Dragon(
            name = name,
            level = lvl,
            type = type,
            hp = HS_HP,
            attack = HS_Atk,
            defense = HS_Def,
            speed = HS_Spd,
            attacks = MoveSet.firstOrNull() ?: "",
            imageResId = imageResId,
            hiddenStats = Dragon.HiddenStats(HS_HP, HS_Atk, HS_Def, HS_Spd)
        )
    }
}