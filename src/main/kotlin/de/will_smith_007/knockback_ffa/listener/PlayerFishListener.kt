package de.will_smith_007.knockback_ffa.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.event.player.PlayerFishEvent.State
import org.bukkit.util.Vector

class PlayerFishListener : Listener {

    @EventHandler
    fun onPlayerFish(playerFishEvent: PlayerFishEvent) {
        val player: Player = playerFishEvent.player
        val state: State = playerFishEvent.state
        if (state != State.IN_GROUND) return

        player.velocity = player.location.direction.multiply(2).add(Vector(0.00, 0.75, 0.00))
    }
}