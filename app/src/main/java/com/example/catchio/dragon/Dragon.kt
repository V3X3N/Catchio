package com.example.catchio.dragon

import com.example.catchio.R
import kotlin.math.floor
import kotlin.random.Random

data class Dragon(
    val name: String,
    val type: String,
    val attacks: List<String>,
    val imageResId: Int,
    val baseHp: Int,
    val baseAttack: Int,
    val baseDefense: Int,
    val baseSpeed: Int,
    val minLevel: Int,
    val maxLevel: Int
) {
    private val hiddenHp: Int
        get() = (1..25).random()

    private val hiddenAttack: Int
        get() = (1..25).random()

    private val hiddenDefense: Int
        get() = (1..25).random()

    private val hiddenSpeed: Int
        get() = (1..25).random()

    val level: Int
        get() = Random.nextInt(minLevel, maxLevel + 1)

    val hp: Int
        get() = calculateStat(baseHp, hiddenHp)

    val attack: Int
        get() = calculateStat(baseAttack, hiddenAttack)

    val defense: Int
        get() = calculateStat(baseDefense, hiddenDefense)

    val speed: Int
        get() = calculateStat(baseSpeed, hiddenSpeed)

    private fun calculateStat(base: Int, hidden: Int): Int {
        val calculatedStat = 0.1f * ((1.8f * base + (0.33f * hidden)) * level)
        return floor(calculatedStat).toInt()
    }

    companion object {
        private fun createDragon(
            name: String,
            type: String,
            attacks: List<String>,
            imageResId: Int,
            baseHp: Int,
            baseAttack: Int,
            baseDefense: Int,
            baseSpeed: Int,
            minLevel: Int,
            maxLevel: Int
        ): Dragon {
            require(minLevel <= maxLevel)
            return Dragon(name, type, attacks, imageResId, baseHp, baseAttack, baseDefense, baseSpeed, minLevel, maxLevel)
        }

        fun createFerxe(minLevel: Int, maxLevel: Int): Dragon = createDragon(
            "Ferxe", "Fire",
            listOf("Wing Attack", "Fireball", "Heat Wave", "Claw Swipe"),
            R.drawable.ferxe,
            baseHp = 18, baseAttack = 22, baseDefense = 17, baseSpeed = 28,
            minLevel, maxLevel
        )

        fun createItu(minLevel: Int, maxLevel: Int): Dragon = createDragon(
            "Itu", "Steel",
            listOf("Wing Attack", "Fireball", "Heat Wave", "Claw Swipe"),
            R.drawable.itu,
            baseHp = 20, baseAttack = 22, baseDefense = 18, baseSpeed = 25,
            minLevel, maxLevel
        )

        fun createMiurfinn(minLevel: Int, maxLevel: Int): Dragon = createDragon(
            "Miurfinn", "Poison",
            listOf("Flamethrower", "Inferno", "Fire Blast", "Ember"),
            R.drawable.miurfinn,
            baseHp = 15, baseAttack = 18, baseDefense = 12, baseSpeed = 24,
            minLevel, maxLevel
        )

        fun createSoeshi(minLevel: Int, maxLevel: Int): Dragon = createDragon(
            "Soeshi", "Water",
            listOf("Ice Shard", "Blizzard", "Frost Breath", "Ice Beam"),
            R.drawable.soeshi,
            baseHp = 18, baseAttack = 20, baseDefense = 16, baseSpeed = 26,
            minLevel, maxLevel
        )

        fun createTapree(minLevel: Int, maxLevel: Int): Dragon = createDragon(
            "Tapree", "Grass",
            listOf("Thunderbolt", "Thunder", "Electric Storm", "Shock Wave"),
            R.drawable.tapree,
            baseHp = 22, baseAttack = 24, baseDefense = 20, baseSpeed = 30,
            minLevel, maxLevel
        )
    }
}