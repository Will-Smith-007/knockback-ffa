package de.will_smith_007.knockback_ffa

import com.google.inject.Guice
import de.will_smith_007.knockback_ffa.commands.KnockbackFFACommand
import de.will_smith_007.knockback_ffa.dependencyInjection.InjectionModule
import de.will_smith_007.knockback_ffa.fileConfig.KnockbackConfig
import de.will_smith_007.knockback_ffa.listener.*
import de.will_smith_007.knockback_ffa.scheduler.ScoreboardUpdateScheduler
import de.will_smith_007.knockback_ffa.scheduler.WorldChangerScheduler
import de.will_smith_007.knockback_ffa.sql.interfaces.DatabaseConnector
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    private val injector = Guice.createInjector(InjectionModule(this))

    override fun onEnable() {
        injector.getInstance(KnockbackConfig::class.java)

        registerCommand("knockback", injector.getInstance(KnockbackFFACommand::class.java))

        registerListeners(
            injector.getInstance(PlayerConnectionListener::class.java),
            FoodLevelChangeListener(),
            FallDamageListener(),
            EntitySpawnListener(),
            injector.getInstance(EntityDamageByEntityListener::class.java),
            injector.getInstance(PlayerMoveDeathListener::class.java),
            PlayerDropItemListener(),
            GrapplingHookListener(),
            ItemDurabilityDamageListener()
        )

        injector.getInstance(WorldChangerScheduler::class.java).start()
        injector.getInstance(ScoreboardUpdateScheduler::class.java).start()

        injector.getInstance(DatabaseConnector::class.java).connect()

        logger.info("Have fun playing Knockback-FFA!")
    }

    override fun onDisable() {
        injector.getInstance(WorldChangerScheduler::class.java).stop()
        injector.getInstance(ScoreboardUpdateScheduler::class.java).stop()

        injector.getInstance(DatabaseConnector::class.java).closeConnection()

        logger.info("Bye!")
    }

    private fun registerListeners(vararg listeners: Listener) {
        val pluginManager = Bukkit.getPluginManager()
        for (listener in listeners) {
            pluginManager.registerEvents(listener, this)
        }
    }

    private fun registerCommand(command: String, commandExecutor: CommandExecutor) {
        val pluginCommand: PluginCommand = getCommand(command) ?: return
        pluginCommand.setExecutor(commandExecutor)
    }
}
