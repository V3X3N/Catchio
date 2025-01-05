package com.example.catchio.dragon

import com.example.catchio.R

data class Dragon(
    val name: String,
    val type: String,
    val levelRange: IntRange,
    val statRange: IntRange,
    val attacks: List<String>,
    val imageResId: Int
) {
    val level: Int
        get() = levelRange.random()

    val hp: Int
        get() = statRange.random()

    val attack: Int
        get() = statRange.random()

    val defense: Int
        get() = statRange.random()

    val speed: Int
        get() = statRange.random()

    companion object {
        fun createFerxe(): Dragon = Dragon(
            "Ferxe", "Fire", 5..15, 10..25, listOf("Wing Attack", "Fireball", "Heat Wave", "Claw Swipe"), R.drawable.ferxe
        )

        fun createItu(): Dragon = Dragon(
            "Itu", "Steel", 5..15, 10..25, listOf("Wing Attack", "Fireball", "Heat Wave", "Claw Swipe"), R.drawable.itu
        )

        fun createMiurfinn(): Dragon = Dragon(
            "Miurfinn", "Poison", 1..10, 5..20, listOf("Flamethrower", "Inferno", "Fire Blast", "Ember"), R.drawable.miurfinn
        )

        fun createSoeshi(): Dragon = Dragon(
            "Soeshi", "Water", 3..12, 7..22, listOf("Ice Shard", "Blizzard", "Frost Breath", "Ice Beam"), R.drawable.soeshi
        )

        fun createTapree(): Dragon = Dragon(
            "Tapree", "Grass", 7..18, 12..28, listOf("Thunderbolt", "Thunder", "Electric Storm", "Shock Wave"), R.drawable.tapree
        )
    }
}

