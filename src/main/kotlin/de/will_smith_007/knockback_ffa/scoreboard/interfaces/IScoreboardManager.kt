package de.will_smith_007.knockback_ffa.scoreboard.interfaces

import org.bukkit.entity.Player

interface IScoreboardManager {

    fun createScoreboard(player: Player)

    fun updateScoreboard(player: Player)

    fun setTablist(player: Player)

    fun setScoreboardAndTablist(player: Player)
}