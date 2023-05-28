package de.will_smith_007.knockback_ffa.playerStats

import java.text.DecimalFormat

class PlayerStats(
    private var kills: Int,
    private var deaths: Int
) {

    private val decimalFormat = DecimalFormat("0.#")

    /**
     * Adds one kill to the statistics.
     */
    fun addKill() {
        kills += 1
    }

    /**
     * Adds one death to the statistics.
     */
    fun addDeath() {
        deaths += 1
    }

    /**
     * Gets the kills.
     * @return Amount of kills.
     */
    fun getKills(): Int {
        return kills
    }

    /**
     * Gets the deaths.
     * @return Amount of deaths.
     */
    fun getDeaths(): Int {
        return deaths
    }

    /**
     * Calculates the k/d.
     * @return Calculated k/d.
     */
    fun getKD(): String {
        return decimalFormat.format((kills / deaths))
    }
}