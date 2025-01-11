package com.vcoffee.catchio.screens.backpack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vcoffee.catchio.SharedPreferencesHelper
import com.vcoffee.catchio.dragon.Dragon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BackpackScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferencesHelper = SharedPreferencesHelper(application)
    private val _caughtDragons = MutableStateFlow<List<Dragon>>(emptyList())
    val caughtDragons: StateFlow<List<Dragon>> = _caughtDragons

    init {
        loadDragons()
        reloadDragons()
    }

    private fun loadDragons() {
        val dragons = sharedPreferencesHelper.loadDragons()
        _caughtDragons.value = dragons
    }

    private fun reloadDragons() {
        loadDragons()
    }
}