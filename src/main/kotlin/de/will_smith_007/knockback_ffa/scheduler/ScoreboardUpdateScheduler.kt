package de.will_smith_007.knockback_ffa.scheduler

import com.google.inject.Inject
import com.google.inject.Singleton
import de.will_smith_007.knockback_ffa.scheduler.interfaces.Scheduler
import de.will_smith_007.knockback_ffa.scoreboard.interfaces.ScoreboardManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@Singleton
class ScoreboardUpdateScheduler @Inject constructor(
    private val javaPlugin: JavaPlugin,
    private val scoreboardManager: ScoreboardManager
) : Scheduler {

    private var taskID: Int = 0

    override fun start() {
        taskID = bukkitScheduler.scheduleSyncRepeatingTask(javaPlugin, {
            for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                scoreboardManager.updateScoreboard(onlinePlayer)
            }
        }, 0L, 20L)
    }

    override fun stop() {
        bukkitScheduler.cancelTask(taskID)
    }
}