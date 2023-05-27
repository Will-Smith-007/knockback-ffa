package de.will_smith_007.knockback_ffa.playerStats

import java.text.DecimalFormat

class PlayerStats(
    private var kills: Int,
    private var deaths: Int
) {

    private val decimalFormat = DecimalFormat("0.#")

    fun addKill() {
        kills += 1
    }

    fun addDeath() {
        deaths += 1
    }

    fun getKills(): Int {
        return kills
    }

    fun getDeaths(): Int {
        return deaths
    }

    fun getKD(): String {
        return decimalFormat.format((kills / deaths))
    }
}