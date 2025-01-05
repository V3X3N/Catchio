package com.example.catchio.dragon

import com.example.catchio.R
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
        val hpBonus: Int,
        val attackBonus: Int,
        val defenseBonus: Int,
        val speedBonus: Int
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

            // Generate level within the specified range
            val level = Random.nextInt(levelMin, levelMax + 1)

            // Generate hidden stats
            val hiddenStats = HiddenStats(
                hpBonus = Random.nextInt(1, 26),
                attackBonus = Random.nextInt(1, 26),
                defenseBonus = Random.nextInt(1, 26),
                speedBonus = Random.nextInt(1, 26)
            )

            // Calculate final stats
            val finalHp = stats.baseHp + hiddenStats.hpBonus
            val finalAttack = stats.baseAttack + hiddenStats.attackBonus
            val finalDefense = stats.baseDefense + hiddenStats.defenseBonus
            val finalSpeed = stats.baseSpeed + hiddenStats.speedBonus

            return Dragon(
                name = name,
                level = level,
                type = stats.type,
                hp = finalHp,
                attack = finalAttack,
                defense = finalDefense,
                speed = finalSpeed,
                attacks = stats.attackName,
                imageResId = stats.imageResId,
                hiddenStats = hiddenStats
            )
        }

        data class DragonStats(
            val type: String,
            val attackName: String,
            val imageResId: Int,
            val baseHp: Int,
            val baseAttack: Int,
            val baseDefense: Int,
            val baseSpeed: Int
        )
    }
}