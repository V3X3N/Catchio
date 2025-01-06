package com.example.catchio.screens

data class Town(
    val townData: TownData
) {
    data class TownData(
        val dragonName: String? = null,
        val levelRange: IntRange? = null,
        val imageResId: Int? = null
    )
}
