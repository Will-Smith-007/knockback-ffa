package de.will_smith_007.knockback_ffa

import com.google.inject.Guice
import de.will_smith_007.knockback_ffa.dependency_injection.InjectionModule
import de.will_smith_007.knockback_ffa.file_config.KnockbackConfig
import org.bukkit.Bukkit
import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    private val injector = Guice.createInjector(InjectionModule(this))

    override fun onEnable() {
        injector.getInstance(KnockbackConfig::class.java)
        logger.info("Have fun playing Knockback-FFA")
    }

    override fun onDisable() {
        logger.info("Bye!")
    }

    private fun registerListeners(listeners: Array<Listener>) {
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
