package de.will_smith_007.knockback_ffa.scheduler

import com.google.inject.Inject
import com.google.inject.Singleton
import de.will_smith_007.knockback_ffa.fileConfig.interfaces.WorldConfig
import de.will_smith_007.knockback_ffa.gameAssets.GameAssets
import de.will_smith_007.knockback_ffa.gameData.GameData
import de.will_smith_007.knockback_ffa.scheduler.interfaces.Scheduler
import org.bukkit.*
import org.bukkit.plugin.java.JavaPlugin

/**
 * This [Scheduler] has the responsibility to handle world changes after every 10 minutes.
 */
@Singleton
class WorldChangerScheduler @Inject constructor(
    private val worldConfig: WorldConfig,
    private val javaPlugin: JavaPlugin,
    private val gameAssets: GameAssets
) : Scheduler {

    private var taskID: Int = 0

    override fun start() {
        taskID = bukkitScheduler.scheduleSyncRepeatingTask(javaPlugin, {
            val worldList: MutableList<String> = worldConfig.getWorlds()
            if (worldList.isEmpty()) return@scheduleSyncRepeatingTask

            val (world) = gameAssets.gameData ?: return@scheduleSyncRepeatingTask

            if (worldList.size == 1) {
                gameAssets.gameData = GameData(world, System.currentTimeMillis())
                return@scheduleSyncRepeatingTask
            }
            worldList.remove(world.name)
            worldList.shuffled()

            // Loads the selected world first and after teleporting the players, the previous played world unloads.
            val selectedWorldName: String = worldList[0]
            loadWorld(selectedWorldName)
            Bukkit.unloadWorld(world, false)
        }, 0L, (20L * 60L) * 10L)
    }

    override fun stop() {
        bukkitScheduler.cancelTask(taskID)
    }

    /**
     * Loads the new selected world and teleports all players to this world.
     * This sets also important game rules such as cancelling daylight cycle and weather cycle.
     */
    private fun loadWorld(worldName: String) {
        val world: World = Bukkit.createWorld(WorldCreator(worldName))
            ?: return
        val worldSpawnLocation: Location = worldConfig.getWorldSpawnLocation(worldName)
            ?: return

        // Sets time to day and clears the weather
        world.time = 1200L
        world.weatherDuration = 1200
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false)
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)

        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            onlinePlayer.teleport(worldSpawnLocation)
        }
        gameAssets.gameData = GameData(world, System.currentTimeMillis())
    }
}