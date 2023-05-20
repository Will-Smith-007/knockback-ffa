package de.will_smith_007.knockback_ffa.listener

import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

class PlayerDropItemListener : Listener {

    @EventHandler
    fun onPlayerDropItem(playerDropItemEvent: PlayerDropItemEvent) {
        val player: Player = playerDropItemEvent.player
        if (player.gameMode == GameMode.CREATIVE) return

        playerDropItemEvent.isCancelled = true
    }
}