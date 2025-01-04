package com.example.catchio.screens

import com.example.catchio.dragon.Dragon

sealed class Town {
    data class TownData(val dragon: Dragon? = null, val imageResId: Int? = null) : Town()
}