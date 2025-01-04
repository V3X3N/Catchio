package com.example.catchio.screens

sealed class Town {
    data class TownImage(val imageResId: Int) : Town()
}