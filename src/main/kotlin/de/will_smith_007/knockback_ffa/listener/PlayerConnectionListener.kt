package de.will_smith_007.knockback_ffa.listener

import com.google.inject.Inject
import de.will_smith_007.knockback_ffa.enums.Message
import de.will_smith_007.knockback_ffa.kit.KitHandler
import net.kyori.adventure.text.Component
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerConnectionListener @Inject constructor(
    private val kitHandler: KitHandler
) : Listener {

    @EventHandler
    fun onPlayerJoin(playerJoinEvent: PlayerJoinEvent) {
        val player: Player = playerJoinEvent.player
        player.gameMode = GameMode.ADVENTURE
        playerJoinEvent.joinMessage(Component.text("${Message.PREFIX}§e${player.name}§a joined the game!"))

        kitHandler.setPlayerKit(player)
    }

    @EventHandler
    fun onPlayerQuit(playerQuitEvent: PlayerQuitEvent) {
        val player: Player = playerQuitEvent.player
        playerQuitEvent.quitMessage(Component.text("${Message.PREFIX}§e${player.name}§c left the game."))
    }
}