package de.will_smith_007.knockback_ffa.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class FallDamageListener : Listener {

    @EventHandler
    fun onFallDamage(entityDamageEvent: EntityDamageEvent) {
        if (entityDamageEvent.entity !is Player) return
        if (entityDamageEvent.cause == EntityDamageEvent.DamageCause.FALL) {
            entityDamageEvent.isCancelled = true
        }
    }
}