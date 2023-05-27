package de.will_smith_007.knockback_ffa.gameAssets

import com.google.inject.Singleton
import de.will_smith_007.knockback_ffa.damageData.DamageData
import de.will_smith_007.knockback_ffa.gameData.GameData
import de.will_smith_007.knockback_ffa.playerStats.PlayerStats
import org.bukkit.entity.Player
import java.util.UUID

@Singleton
class GameAssets {

    val lastDamageData: HashMap<Player, DamageData> = HashMap()
    val playerStatsMap: HashMap<UUID, PlayerStats> = HashMap()
    var gameData: GameData? = null
}