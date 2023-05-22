package de.will_smith_007.knockback_ffa.scheduler

import com.google.inject.Inject
import com.google.inject.Singleton
import de.will_smith_007.knockback_ffa.fileConfig.interfaces.IWorldConfig
import de.will_smith_007.knockback_ffa.gameAssets.GameAssets
import de.will_smith_007.knockback_ffa.gameData.GameData
import de.will_smith_007.knockback_ffa.scheduler.interfaces.IScheduler
import org.bukkit.*
import org.bukkit.plugin.java.JavaPlugin

@Singleton
class WorldChangerScheduler @Inject constructor(
    private val worldConfig: IWorldConfig,
    private val javaPlugin: JavaPlugin,
    private val gameAssets: GameAssets
) : IScheduler {

    private var taskID: Int = 0

    override fun start() {
        taskID = bukkitScheduler.scheduleSyncRepeatingTask(javaPlugin, {
            val worldList: MutableList<String> = worldConfig.getWorlds()
            if (worldList.isEmpty()) return@scheduleSyncRepeatingTask

            val gameData: GameData = gameAssets.gameData ?: return@scheduleSyncRepeatingTask
            val world: World = gameData.world

            if (worldList.size == 1) {
                gameAssets.gameData = GameData(world, System.currentTimeMillis())
                return@scheduleSyncRepeatingTask
            }
            worldList.remove(world.name)
            worldList.shuffled()

            val selectedWorldName: String = worldList[0]
            loadWorld(selectedWorldName)
            Bukkit.unloadWorld(world, false)
        }, 0L, (20L * 60L) * 10L)
    }

    override fun stop() {
        bukkitScheduler.cancelTask(taskID)
    }

    private fun loadWorld(worldName: String) {
        val world: World = Bukkit.createWorld(WorldCreator(worldName))
            ?: return
        val worldSpawnLocation: Location = worldConfig.getWorldSpawnLocation(worldName)
            ?: return

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