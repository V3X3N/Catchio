package com.vcoffee.catchio.screens.backpack

import android.app.Application
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
    val caughtDragons: StateFlow<List<Dragon>> = _caughtDragons

    init {
        viewModelScope.launch {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid
                loadDragons(userId)
            } else {
                _caughtDragons.value = emptyList()
            }
        }
    }

    private suspend fun loadDragons(userId: String) {
        val stable = dragonRepository.getDragonStable(userId)
        if (stable != null && stable.dragons.isNotEmpty()) {
            val firestoreDragons = dragonRepository.getDragonsFromStable(stable.dragons)
            _caughtDragons.value = firestoreDragons.map { it.toDragon() }
        } else {
            _caughtDragons.value = emptyList()
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