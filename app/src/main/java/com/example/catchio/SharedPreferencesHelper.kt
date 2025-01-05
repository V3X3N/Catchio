package com.example.catchio

import android.content.Context
import com.example.catchio.dragon.Dragon
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("dragon_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveDragons(dragons: List<Dragon>) {
        val json = gson.toJson(dragons)
        sharedPreferences.edit().putString("dragons", json).apply()
    }

    fun loadDragons(): List<Dragon> {
        val json = sharedPreferences.getString("dragons", null) ?: return emptyList()
        val type = object : TypeToken<List<Dragon>>() {}.type
        return gson.fromJson(json, type)
    }
}
