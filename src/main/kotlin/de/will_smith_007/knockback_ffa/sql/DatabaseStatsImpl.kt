package de.will_smith_007.knockback_ffa.sql

import com.google.inject.Inject
import com.google.inject.Singleton
import de.will_smith_007.knockback_ffa.playerStats.PlayerStats
import de.will_smith_007.knockback_ffa.sql.interfaces.DatabaseProvider
import de.will_smith_007.knockback_ffa.sql.interfaces.DatabaseStats
import java.sql.ResultSet
import java.util.*
import java.util.concurrent.CompletableFuture

@Singleton
class DatabaseStatsImpl @Inject constructor(
    private val databaseProvider: DatabaseProvider
) : DatabaseStats {

    override fun getStats(uuid: UUID): CompletableFuture<PlayerStats> {
        val sqlQuery = "SELECT * FROM knockback WHERE uuid = ?"
        return CompletableFuture.supplyAsync {
            databaseProvider.preparedStatement(sqlQuery).use {
                it?.setString(1, uuid.toString())

                val resultSet: ResultSet = it?.executeQuery() ?: return@supplyAsync PlayerStats(0, 0)
                if (resultSet.next()) {
                    val kills: Int = resultSet.getInt("kills")
                    val deaths: Int = resultSet.getInt("deaths")
                    return@supplyAsync PlayerStats(kills, deaths)
                }
            }
            return@supplyAsync PlayerStats(0, 0)
        }
    }

    override fun saveStats(uuid: UUID, playerStats: PlayerStats) {
        val sqlQuery = "INSERT INTO knockback (uuid, kills, deaths) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE kills = ?, deaths = ?;"
        databaseProvider.preparedStatement(sqlQuery).use {
            it?.setString(1, uuid.toString())
            it?.setInt(2, playerStats.getKills())
            it?.setInt(3, playerStats.getDeaths())

            it?.setInt(4, playerStats.getKills())
            it?.setInt(5, playerStats.getDeaths())
            it?.executeUpdate()
        }
    }
}