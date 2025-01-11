package com.vcoffee.catchio.dragon

import com.google.firebase.firestore.DocumentReference
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
        val dragonData = mapOf(
            "Ferxe" to DragonStats("Fire", "Fire punch", R.drawable.ferxe, 11, 15, 7, 7, 2, 3, 1, 0),
            "Itu" to DragonStats("Air", "Air punch", R.drawable.itu, 10, 17, 3, 10, 1, 2, 0, 1),
            "Tapree" to DragonStats("Grass", "Grass punch", R.drawable.tapree, 13, 10, 12, 5, 3, 1, 2, 0),
            "Soeshi" to DragonStats("Water", "Water punch", R.drawable.soeshi, 8, 14, 7, 11, 0, 2, 1, 2),
            "Miurfinn" to DragonStats("Poison", "Poison punch", R.drawable.miurfinn, 13, 7, 8, 12, 2, 0, 1, 3),
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
            val baseSPD: Int,
            val baseHPBonus: Int,
            val baseATKBonus: Int,
            val baseDEFBonus: Int,
            val baseSPDBonus: Int
        )
    }
}

data class FireStoreDragon(
    val name: String = "",
    val lvl: Int = 0,
    val type: String = "",
    val HS_HP: Int = 0,
    val HS_Atk: Int = 0,
    val HS_Def: Int = 0,
    val HS_Spd: Int = 0,
    val MoveSet: List<String> = emptyList(),
    val baseHP: Int = 0,
    val baseATK: Int = 0,
    val baseDEF: Int = 0,
    val baseSPD: Int = 0
)

data class DragonStable(
    val userRef: DocumentReference? = null,
    val dragons: List<DocumentReference> = emptyList()
)