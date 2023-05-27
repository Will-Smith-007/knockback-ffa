package de.will_smith_007.knockback_ffa.commands

import com.google.inject.Inject
import de.will_smith_007.knockback_ffa.enums.Message
import de.will_smith_007.knockback_ffa.gameAssets.GameAssets
import de.will_smith_007.knockback_ffa.isPlayer
import de.will_smith_007.knockback_ffa.playerStats.PlayerStats
import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.util.UUID

class StatsCommand @Inject constructor(
    private val gameAssets: GameAssets
): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (!sender.isPlayer()) {
            sender.sendMessage(Component.text("${Message.PREFIX}You must be a player to execute this command."))
            return true
        }

        if (args?.size != 0) {
            sender.sendMessage(Component.text("${Message.PREFIX}Please use the following command: " +
                    "/stats"))
            return true
        }

        val uuid: UUID = sender.uniqueId
        val playerStats: PlayerStats = gameAssets.playerStatsMap[uuid] ?: PlayerStats(0, 0)

        sender.sendMessage(Component.text("${Message.PREFIX}Kills: §e${playerStats.getKills()}"))
        sender.sendMessage(Component.text("${Message.PREFIX}Deaths: §e${playerStats.getDeaths()}"))
        sender.sendMessage(Component.text("${Message.PREFIX}K/D: §e${playerStats.getKD()}"))

        return true
    }
}