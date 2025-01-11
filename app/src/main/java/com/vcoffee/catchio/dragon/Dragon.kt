package com.vcoffee.catchio.dragon

import com.vcoffee.catchio.R
import kotlin.random.Random

data class Dragon(
    val name: String,
    val level: Int,
    val type: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val speed: Int,
    val attacks: String,
    val imageResId: Int,
    val hiddenStats: HiddenStats
) {
    data class HiddenStats(
        val hiddenHP: Int,
        val hiddenATK: Int,
        val hiddenDEF: Int,
        val hiddenSPD: Int
    )

    companion object {
        private val dragonData = mapOf(
            "Ferxe" to DragonStats("Fire", "Fire punch", R.drawable.ferxe, 11, 15, 7, 7),
            "Itu" to DragonStats("Air", "Air punch", R.drawable.itu, 10, 17, 3, 10),
            "Tapree" to DragonStats("Grass", "Grass punch", R.drawable.tapree, 13, 10, 12, 5),
            "Soeshi" to DragonStats("Water", "Water punch", R.drawable.soeshi, 8, 14, 7, 11),
            "Miurfinn" to DragonStats("Poison", "Poison punch", R.drawable.miurfinn, 13, 7, 8, 12),
        )

        fun create(name: String, levelMin: Int, levelMax: Int): Dragon {
            val stats = dragonData[name] ?: throw IllegalArgumentException("Unknown dragon name: $name")

            val level = Random.nextInt(levelMin, levelMax + 1)

            val hiddenStats = HiddenStats(
                hiddenHP = Random.nextInt(1, 26),
                hiddenATK = Random.nextInt(1, 26),
                hiddenDEF = Random.nextInt(1, 26),
                hiddenSPD = Random.nextInt(1, 26)
            )

            val finalHP = (0.1f * (1.8f * stats.baseHP + (0.33f * hiddenStats.hiddenHP)) * level).toInt()
            val finalATK = (0.1f * (1.8f * stats.baseATK + (0.33f * hiddenStats.hiddenATK)) * level).toInt()
            val finalDEF = (0.1f * (1.8f * stats.baseDEF + (0.33f * hiddenStats.hiddenDEF)) * level).toInt()
            val finalSPD = (0.1f * (1.8f * stats.baseSPD + (0.33f * hiddenStats.hiddenSPD)) * level).toInt()

            return Dragon(
                name = name,
                level = level,
                type = stats.type,
                hp = finalHP,
                attack = finalATK,
                defense = finalDEF,
                speed = finalSPD,
                attacks = stats.attackName,
                imageResId = stats.imageResId,
                hiddenStats = hiddenStats
            )
        }

        data class DragonStats(
            val type: String,
            val attackName: String,
            val imageResId: Int,
            val baseHP: Int,
            val baseATK: Int,
            val baseDEF: Int,
            val baseSPD: Int
        )
    }
}