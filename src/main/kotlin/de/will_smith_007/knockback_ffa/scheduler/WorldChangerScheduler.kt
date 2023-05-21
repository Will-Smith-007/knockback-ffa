package de.will_smith_007.knockback_ffa.scheduler

import com.google.inject.Inject
import com.google.inject.Singleton
import de.will_smith_007.knockback_ffa.file_config.KnockbackConfig
import de.will_smith_007.knockback_ffa.game_assets.GameAssets
import de.will_smith_007.knockback_ffa.game_data.GameData
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.CompletableFuture

@Singleton
class WorldChangerScheduler @Inject constructor(
    private val knockbackConfig: KnockbackConfig,
    private val javaPlugin: JavaPlugin,
    private val gameAssets: GameAssets
) : IScheduler {

    private var taskID: Int = 0

    override fun start() {
        taskID = bukkitScheduler.scheduleSyncRepeatingTask(javaPlugin, {
            val worldList: List<String> = knockbackConfig.getWorlds()
            if (worldList.isEmpty()) return@scheduleSyncRepeatingTask

            val gameData: GameData = gameAssets.gameData ?: return@scheduleSyncRepeatingTask
            val world: World = gameData.world

            if (worldList.size == 1) {
                gameAssets.gameData = GameData(world, System.currentTimeMillis())
                return@scheduleSyncRepeatingTask
            }
            worldList.minus(world.name)
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
        val worldSpawnLocation: Location = knockbackConfig.getWorldSpawnLocation(worldName)
            ?: return
        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            onlinePlayer.teleport(worldSpawnLocation)
        }
        gameAssets.gameData = GameData(world, System.currentTimeMillis())
    }
}