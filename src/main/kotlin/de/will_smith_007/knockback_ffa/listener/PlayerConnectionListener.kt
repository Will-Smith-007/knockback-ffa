package de.will_smith_007.knockback_ffa.listener

import com.google.inject.Inject
import de.will_smith_007.knockback_ffa.enums.Message
import de.will_smith_007.knockback_ffa.fileConfig.interfaces.WorldConfig
import de.will_smith_007.knockback_ffa.gameAssets.GameAssets
import de.will_smith_007.knockback_ffa.gameData.GameData
import de.will_smith_007.knockback_ffa.kit.KitHandler
import de.will_smith_007.knockback_ffa.scoreboard.interfaces.ScoreboardManager
import net.kyori.adventure.text.Component
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerConnectionListener @Inject constructor(
    private val kitHandler: KitHandler,
    private val scoreboardManager: ScoreboardManager,
    private val worldConfig: WorldConfig,
    private val gameAssets: GameAssets
) : Listener {

    @EventHandler
    fun onPlayerJoin(playerJoinEvent: PlayerJoinEvent) {
        val player: Player = playerJoinEvent.player
        player.gameMode = GameMode.ADVENTURE
        playerJoinEvent.joinMessage(Component.text("${Message.PREFIX}§c${player.name}§a joined the game!"))

        kitHandler.setPlayerKit(player)
        scoreboardManager.setScoreboardAndTablist(player)

        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.name == player.name) continue
            scoreboardManager.setTablist(onlinePlayer)
        }

        val playedWorld: World = handleFirstWorldSelection() ?: return
        val worldSpawnLocation: Location = worldConfig.getWorldSpawnLocation(playedWorld.name) ?: return

        player.teleport(worldSpawnLocation)
    }

    @EventHandler
    fun onPlayerQuit(playerQuitEvent: PlayerQuitEvent) {
        val player: Player = playerQuitEvent.player
        playerQuitEvent.quitMessage(Component.text("${Message.PREFIX}§c${player.name}§c left the game."))

        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.name == player.name) continue
            scoreboardManager.setTablist(onlinePlayer)
        }
    }

    private fun handleFirstWorldSelection(): World? {
        val worldList: List<String> = worldConfig.getWorlds()
        if (worldList.isEmpty()) return null
        if (gameAssets.gameData != null) return null

        worldList.shuffled()

        val selectedWorldName = worldList[0]
        val world: World = Bukkit.createWorld(WorldCreator(selectedWorldName)) ?: return null

        world.time = 1200L
        world.weatherDuration = 1200
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false)
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)

        gameAssets.gameData = GameData(world, System.currentTimeMillis())
        return world
    }
}