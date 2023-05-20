package de.will_smith_007.knockback_ffa.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemDamageEvent

class PlayerItemDamageListener : Listener {

    @EventHandler
    fun onPlayerItemDamage(playerItemDamageEvent: PlayerItemDamageEvent) {
        playerItemDamageEvent.isCancelled = true
    }
}