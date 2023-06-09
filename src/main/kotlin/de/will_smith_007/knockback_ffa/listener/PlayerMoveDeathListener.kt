package de.will_smith_007.knockback_ffa.listener

import com.google.inject.Inject
import de.will_smith_007.knockback_ffa.damageData.DamageData
import de.will_smith_007.knockback_ffa.fileConfig.interfaces.WorldConfig
import de.will_smith_007.knockback_ffa.gameAssets.GameAssets
import de.will_smith_007.knockback_ffa.gameData.GameData
import de.will_smith_007.knockback_ffa.playerStats.PlayerStats
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class PlayerMoveDeathListener @Inject constructor(
    private val gameAssets: GameAssets,
    private val worldConfig: WorldConfig
) : Listener {

    @EventHandler
    fun onPlayerMove(playerMoveEvent: PlayerMoveEvent) {
        val player: Player = playerMoveEvent.player
        val gameData: GameData = gameAssets.gameData ?: return

        val world: World = gameData.world
        val worldName: String = world.name
        val deathHeight: Int = worldConfig.getDeathHeight(worldName)
        val playerLocation: Location = player.location

        if (playerLocation.blockY > deathHeight) return

        val worldSpawnLocation: Location = worldConfig.getWorldSpawnLocation(worldName) ?: return
        val lastDamageData: HashMap<Player, DamageData> = gameAssets.lastDamageData
        val damageData: DamageData? = lastDamageData[player]
        val currentTimeMillis: Long = System.currentTimeMillis()

        player.teleport(worldSpawnLocation)

        val playerStatsMap = gameAssets.playerStatsMap
        if (damageData == null || (currentTimeMillis - damageData.lastDamageMillis) > 5000) {
            // Death handling if the last damage is more than 5 seconds ago.
            player.showTitle(
                Title.title(
                    Component.text("✘ You died ✘", NamedTextColor.RED),
                    Component.text(player.name, NamedTextColor.AQUA)
                )
            )
            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f)

            val uuid = player.uniqueId
            val playerStats: PlayerStats = playerStatsMap[uuid] ?: return
            playerStats.addDeath()
        } else {
            val damagePlayer: Player = damageData.lastDamagePlayer

            // Player which was pushed from the world
            player.showTitle(
                Title.title(
                    Component.text("✘ You died ✘", NamedTextColor.RED),
                    Component.text(damagePlayer.name, NamedTextColor.AQUA)
                )
            )
            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f)

            // Player which pushed the victim player from the world
            damagePlayer.showTitle(
                Title.title(
                    Component.text("✔ You killed ✔", NamedTextColor.GREEN),
                    Component.text(player.name, NamedTextColor.AQUA)
                )
            )
            damagePlayer.playSound(damagePlayer.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 0.0f)

            handlePlayerStats(player, damagePlayer)
        }
    }

    /**
     * Adds a death to the victim player and a kill to the last damage player.
     * Note that this is not a database call! The data will be stored to the database if a player disconnects.
     */
    private fun handlePlayerStats(victimPlayer: Player, damagePlayer: Player) {
        val playerStatsMap = gameAssets.playerStatsMap

        val damageUuid = damagePlayer.uniqueId
        val victimUuid = victimPlayer.uniqueId

        var damagePlayerStats: PlayerStats? = playerStatsMap[damageUuid]
        var victimPlayerStats: PlayerStats? = playerStatsMap[victimUuid]

        if (damagePlayerStats == null) {
            damagePlayerStats = PlayerStats(1, 0)
            playerStatsMap[damageUuid] = damagePlayerStats
        } else {
            damagePlayerStats.addKill()
        }

        if (victimPlayerStats == null) {
            victimPlayerStats = PlayerStats(0, 1)
            playerStatsMap[victimUuid] = victimPlayerStats
        } else {
            victimPlayerStats.addDeath()
        }
    }
}