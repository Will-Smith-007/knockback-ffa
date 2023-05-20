package de.will_smith_007.knockback_ffa.commands

import com.google.inject.Inject
import de.will_smith_007.knockback_ffa.enums.Message
import de.will_smith_007.knockback_ffa.file_config.KnockbackConfig
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class KnockbackFFACommand @Inject constructor(
    private val knockbackConfig: KnockbackConfig
): TabExecutor {

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        TODO("Not yet implemented")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Component.text("${Message.PREFIX} §cYou must be a player to execute this command."))
            return true
        }

        if (!sender.hasPermission("knockback.setup")) {
            sender.sendMessage(Component.text("${Message.PREFIX} §cYou don't have permissions to execute " +
                    "this command"))
            return true
        }

        val player: Player = sender

        if (args?.size == 1) {
            if (args[0].equals("setDeath", true)) {
                val worldName: String = args[0]
                if (!knockbackConfig.isConfiguredWorld(worldName)) {
                    player.sendMessage(Component.text("${Message.PREFIX} §cThis world isn't configured."))
                    return true
                }

                val playerHeight: Int = player.location.blockY
                knockbackConfig.setDeathHeight(worldName, playerHeight)
                player.sendMessage(Component.text("${Message.PREFIX} §aYou've set the death height of " +
                        "this world to §e$playerHeight§a."))
            }
        } else if (args?.size == 2) {
            if (args[0].equals("addWorld", true)) {
                val worldName: String = args[1]
                if (knockbackConfig.isConfiguredWorld(worldName)) {
                    player.sendMessage(Component.text("${Message.PREFIX} §cThe world §e$worldName §c" +
                            "already exists."))
                    return true
                }

                val world: World? = Bukkit.createWorld(WorldCreator(worldName))
                if (world == null) {
                    player.sendMessage(Component.text("${Message.PREFIX} §cCouldn't found world §e$worldName§c."))
                    return true
                }

                knockbackConfig.addWorld(world)
                player.sendMessage(Component.text("${Message.PREFIX} §aYou added the world §e$worldName§a."))
            } else if (args[0].equals("removeWorld", true)) {
                val worldName: String = args[1]
                if (!knockbackConfig.isConfiguredWorld(worldName)) {
                    player.sendMessage(Component.text("${Message.PREFIX} §cThere isn't a world §e$worldName" +
                            "§c configured"))
                    return true
                }

                knockbackConfig.removeWorld(worldName)
                player.sendMessage(Component.text("${Message.PREFIX} §aYou removed the world §e$worldName§a."))
            }
        }

        return true
    }
}