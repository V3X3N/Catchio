package com.example.catchio.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.catchio.SharedPreferencesHelper
import com.example.catchio.dragon.Dragon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Screen2ViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferencesHelper = SharedPreferencesHelper(application)

    private val _caughtDragons = MutableStateFlow(sharedPreferencesHelper.loadDragons())
    val caughtDragons: StateFlow<List<Dragon>> = _caughtDragons

    fun loadDragons(): List<Dragon> {
        val dragons = sharedPreferencesHelper.loadDragons()
        _caughtDragons.value = dragons
        return dragons
    }
}

