package de.will_smith_007.knockback_ffa.commands

import com.google.inject.Inject
import de.will_smith_007.knockback_ffa.enums.Message
import de.will_smith_007.knockback_ffa.fileConfig.interfaces.WorldConfig
import de.will_smith_007.knockback_ffa.isPlayer
import net.kyori.adventure.text.Component
import org.bukkit.*
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

class KnockbackFFACommand @Inject constructor(
    private val worldConfig: WorldConfig
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
        if (!sender.isPlayer()) {
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

        if (args?.size == 1) {
            val subCommand = args[0]
            when {
                subCommand.equals("setDeath", true) -> {
                    val world: World = sender.world
                    val worldName: String = world.name

                    if (!worldConfig.isConfiguredWorld(worldName)) {
                        sender.sendMessage(Component.text("${Message.PREFIX}§cThis world isn't configured."))
                        return true
                    }

                    val senderHeight: Int = sender.location.blockY
                    worldConfig.setDeathHeight(worldName, senderHeight)
                    sender.sendMessage(
                        Component.text(
                            "${Message.PREFIX}§aYou've set the death height of " +
                                    "this world to §e$senderHeight§a."
                        )
                    )
                }

                subCommand.equals("setSpawn", true) -> {
                    val world: World = sender.world
                    val worldName: String = world.name

                    if (!worldConfig.isConfiguredWorld(worldName)) {
                        sender.sendMessage(Component.text("${Message.PREFIX}§cThis world isn't configured."))
                        return true
                    }

                    val senderLocation: Location = sender.location
                    worldConfig.setWorldSpawn(worldName, senderLocation)
                    sender.sendMessage(
                        Component.text(
                            "${Message.PREFIX}§aYou've set the world spawn for §e" +
                                    "$worldName§a."
                        )
                    )
                }
            }
        } else if (args?.size == 2) {
            val subCommand = args[0]
            val worldName: String = args[1]
            when {
                subCommand.equals("addWorld", true) -> {
                    if (worldConfig.isConfiguredWorld(worldName)) {
                        sender.sendMessage(
                            Component.text(
                                "${Message.PREFIX}§cThe world §e$worldName §c" +
                                        "already exists."
                            )
                        )
                        return true
                    }

                    val world: World? = Bukkit.createWorld(WorldCreator(worldName))
                    if (world == null) {
                        sender.sendMessage(Component.text("${Message.PREFIX}§cCouldn't found world §e$worldName§c."))
                        return true
                    }

                    worldConfig.addWorld(world)
                    sender.teleport(world.spawnLocation)
                    sender.gameMode = GameMode.CREATIVE
                    sender.isFlying = true
                    sender.sendMessage(Component.text("${Message.PREFIX}§aYou added the world §e$worldName§a."))
                }

                subCommand.equals("removeWorld", true) -> {
                    if (!worldConfig.isConfiguredWorld(worldName)) {
                        sender.sendMessage(
                            Component.text(
                                "${Message.PREFIX}§cThere isn't a world §e$worldName" +
                                        "§c configured"
                            )
                        )
                        return true
                    }

                    worldConfig.removeWorld(worldName)
                    sender.sendMessage(Component.text("${Message.PREFIX}§aYou removed the world §e$worldName§a."))
                }

                subCommand.equals("tp", true) || subCommand.equals("teleport", true) -> {
                    val world: World? = Bukkit.createWorld(WorldCreator(worldName))
                    if (world == null) {
                        sender.sendMessage(Component.text("${Message.PREFIX}§cCouldn't found world §e$worldName§c."))
                        return true
                    }

                    sender.teleport(world.spawnLocation)
                    sender.sendMessage(
                        Component.text(
                            "${Message.PREFIX}§aYou've been teleported to " +
                                    "world §e$worldName§a."
                        )
                    )
                }
            }
        }

        return true
    }
}