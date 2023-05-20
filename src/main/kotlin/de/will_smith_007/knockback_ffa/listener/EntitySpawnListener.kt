package de.will_smith_007.knockback_ffa.listener

import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent

class EntitySpawnListener : Listener {

    @EventHandler
    fun onEntitySpawn(entitySpawnEvent: EntitySpawnEvent) {
        if (entitySpawnEvent.entityType != EntityType.FISHING_HOOK) {
            entitySpawnEvent.isCancelled = true
        }
    }
}