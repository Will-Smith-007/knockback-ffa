package de.will_smith_007.knockback_ffa.commands

import com.google.inject.Inject
import de.will_smith_007.knockback_ffa.enums.Message
import de.will_smith_007.knockback_ffa.file_config.KnockbackConfig
import net.kyori.adventure.text.Component
import org.bukkit.*
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class KnockbackFFACommand @Inject constructor(
    private val knockbackConfig: KnockbackConfig
) : TabExecutor {

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        if (!sender.hasPermission("knockback.setup")) return null
        if (args?.size == 1) {
            return mutableListOf("setDeath", "setSpawn", "addWorld", "removeWorld", "teleport", "tp")
        } else if (args?.size == 2) {
            val subCommand: String = args[0]
            if (subCommand.equals("addWorld", true)
                || subCommand.equals("removeWorld", true)
                || subCommand.equals("teleport", true)
                || subCommand.equals("tp", true)
            ) {
                return mutableListOf("WorldName")
            }
        }
        return null
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Component.text("${Message.PREFIX}§cYou must be a player to execute this command."))
            return true
        }

        if (!sender.hasPermission("knockback.setup")) {
            sender.sendMessage(
                Component.text(
                    "${Message.PREFIX}§cYou don't have permissions to execute " +
                            "this command"
                )
            )
            return true
        }

        val player: Player = sender

        if (args?.size == 1) {
            val subCommand = args[0]
            if (subCommand.equals("setDeath", true)) {
                val world: World = player.world
                val worldName: String = world.name

                if (!knockbackConfig.isConfiguredWorld(worldName)) {
                    player.sendMessage(Component.text("${Message.PREFIX}§cThis world isn't configured."))
                    return true
                }

                val playerHeight: Int = player.location.blockY
                knockbackConfig.setDeathHeight(worldName, playerHeight)
                player.sendMessage(
                    Component.text(
                        "${Message.PREFIX}§aYou've set the death height of " +
                                "this world to §e$playerHeight§a."
                    )
                )
            } else if (subCommand.equals("setSpawn", true)) {
                val world: World = player.world
                val worldName: String = world.name

                if (!knockbackConfig.isConfiguredWorld(worldName)) {
                    player.sendMessage(Component.text("${Message.PREFIX}§cThis world isn't configured."))
                    return true
                }

                val playerLocation: Location = player.location
                knockbackConfig.setWorldSpawn(worldName, playerLocation)
                player.sendMessage(
                    Component.text(
                        "${Message.PREFIX}§aYou've set the world spawn for §e" +
                                "$worldName§a."
                    )
                )
            }
        } else if (args?.size == 2) {
            val subCommand = args[0]
            val worldName: String = args[1]
            if (subCommand.equals("addWorld", true)) {
                if (knockbackConfig.isConfiguredWorld(worldName)) {
                    player.sendMessage(
                        Component.text(
                            "${Message.PREFIX}§cThe world §e$worldName §c" +
                                    "already exists."
                        )
                    )
                    return true
                }

                val world: World? = Bukkit.createWorld(WorldCreator(worldName))
                if (world == null) {
                    player.sendMessage(Component.text("${Message.PREFIX}§cCouldn't found world §e$worldName§c."))
                    return true
                }

                knockbackConfig.addWorld(world)
                player.teleport(world.spawnLocation)
                player.gameMode = GameMode.CREATIVE
                player.isFlying = true
                player.sendMessage(Component.text("${Message.PREFIX}§aYou added the world §e$worldName§a."))
            } else if (subCommand.equals("removeWorld", true)) {
                if (!knockbackConfig.isConfiguredWorld(worldName)) {
                    player.sendMessage(
                        Component.text(
                            "${Message.PREFIX}§cThere isn't a world §e$worldName" +
                                    "§c configured"
                        )
                    )
                    return true
                }

                knockbackConfig.removeWorld(worldName)
                player.sendMessage(Component.text("${Message.PREFIX}§aYou removed the world §e$worldName§a."))
            } else if (subCommand.equals("tp", true)
                || subCommand.equals("teleport", true)
            ) {
                val world: World? = Bukkit.createWorld(WorldCreator(worldName))
                if (world == null) {
                    player.sendMessage(Component.text("${Message.PREFIX}§cCouldn't found world §e$worldName§c."))
                    return true
                }

                player.teleport(world.spawnLocation)
                player.sendMessage(
                    Component.text(
                        "${Message.PREFIX}§aYou've been teleported to " +
                                "world §e$worldName§a."
                    )
                )
            }
        }

        return true
    }
}