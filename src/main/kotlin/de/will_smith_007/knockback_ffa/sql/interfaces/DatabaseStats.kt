package de.will_smith_007.knockback_ffa.sql.interfaces

import de.will_smith_007.knockback_ffa.playerStats.PlayerStats
import java.util.UUID
import java.util.concurrent.CompletableFuture

interface DatabaseStats {

    /**
     * Gets the stored database stats from a specified [UUID] asynchronously.
     * @param uuid [UUID] from which the stats should be loaded.
     */
    fun getStats(uuid: UUID): CompletableFuture<PlayerStats>

    /**
     * Saves the player stats to the database asynchronously.
     * @param uuid [UUID] for which the stats should be saved.
     * @param playerStats Player stats from the [UUID] which should be saved.
     */
    fun saveStats(uuid: UUID, playerStats: PlayerStats)
}