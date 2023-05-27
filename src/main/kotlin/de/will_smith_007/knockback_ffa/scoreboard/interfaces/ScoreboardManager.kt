package de.will_smith_007.knockback_ffa.scoreboard.interfaces

import org.bukkit.entity.Player

interface ScoreboardManager {

    fun createScoreboard(player: Player)

    fun updateScoreboard(player: Player)

    fun setTablist(player: Player)

    fun setScoreboardAndTablist(player: Player)
}