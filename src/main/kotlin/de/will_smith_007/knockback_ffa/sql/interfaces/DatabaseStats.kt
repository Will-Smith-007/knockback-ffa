package de.will_smith_007.knockback_ffa.sql.interfaces

import de.will_smith_007.knockback_ffa.playerStats.PlayerStats
import java.util.UUID
import java.util.concurrent.CompletableFuture

interface DatabaseStats {

    fun getStats(uuid: UUID): CompletableFuture<PlayerStats>

    fun saveStats(uuid: UUID, playerStats: PlayerStats)
}