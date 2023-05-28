package de.will_smith_007.knockback_ffa.scoreboard.interfaces

import org.bukkit.entity.Player

interface ScoreboardManager {

    /**
     * Creates a new sidebar scoreboard for a specific player.
     * @param player Player for which the scoreboard should be created.
     */
    fun createScoreboard(player: Player)

    /**
     * Updates an existing sidebar scoreboard for a specific player.
     * @param player for which the scoreboard should be updated.
     */
    fun updateScoreboard(player: Player)

    /**
     * Sets or updates a tablist for a specific player.
     * @param player Player for which the tablist should be updated or created.
     */
    fun setTablist(player: Player)

    /**
     * Creates a new sidebar scoreboard and creates/updates the tablist.
     * @see ScoreboardManager.createScoreboard
     * @see ScoreboardManager.setTablist
     */
    fun setScoreboardAndTablist(player: Player)
}