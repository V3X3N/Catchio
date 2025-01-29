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
import com.vcoffee.catchio.dragon.DragonUtils
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
                Log.e("BackpackVM", "No logged in user")
                _caughtDragons.value = emptyList()
                _battleDragons.value = emptyList()
            }
        }
    }

    private suspend fun loadDragons(userId: String) {
        try {
            val stable = dragonRepository.getDragonStable(userId)
            if (stable == null) {
                Log.e("BackpackVM", "No stable for user")
                return
            }

            _caughtDragons.value = if (stable.dragons.isNotEmpty()) {
                val dragons = dragonRepository.getDragonsFromStable(stable.dragons)
                dragons.map { it.toDragon() }
            } else {
                emptyList()
            }

            _battleDragons.value = if (stable.battleDragons.isNotEmpty()) {
                val battleDragons = dragonRepository.getDragonsFromStable(stable.battleDragons)
                battleDragons.map { it.toDragon() }
            } else {
                emptyList()
            }

        } catch (e: Exception) {
            Log.e("BackpackVM", "Error loading dragons: ${e.message}")
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

        val finalHP = DragonUtils.calculateStats(baseHP, HS_HP, lvl)
        val finalATK = DragonUtils.calculateStats(baseATK, HS_Atk, lvl)
        val finalDEF = DragonUtils.calculateStats(baseDEF, HS_Def, lvl)
        val finalSPD = DragonUtils.calculateStats(baseSPD, HS_Spd, lvl)

        return Dragon(
            name = name,
            level = lvl,
            type = type,
            hp = finalHP,
            attack = finalATK,
            defense = finalDEF,
            speed = finalSPD,
            attacks = MoveSet.firstOrNull() ?: "",
            imageResId = imageResId,
            hiddenStats = Dragon.HiddenStats(HS_HP, HS_Atk, HS_Def, HS_Spd)
        )
    }
}