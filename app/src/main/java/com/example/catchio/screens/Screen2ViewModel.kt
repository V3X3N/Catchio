package com.example.catchio.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.catchio.SharedPreferencesHelper
import com.example.catchio.dragon.Dragon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Screen2ViewModel(application: Application) : AndroidViewModel(application) {
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