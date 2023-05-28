package de.will_smith_007.knockback_ffa.listener

import com.google.inject.Inject
import de.will_smith_007.knockback_ffa.damageData.DamageData
import de.will_smith_007.knockback_ffa.fileConfig.interfaces.WorldConfig
import de.will_smith_007.knockback_ffa.gameAssets.GameAssets
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

/**
 * This [Listener] has the responsibility to save the last damage data.
 */
class EntityDamageByEntityListener @Inject constructor(
    private val gameAssets: GameAssets,
    private val worldConfig: WorldConfig
) : Listener {

    @EventHandler
    fun onEntityDamageByEntity(entityDamageByEntityEvent: EntityDamageByEntityEvent) {
        if (entityDamageByEntityEvent.damager is Player && entityDamageByEntityEvent.entity is Player) {
            val victimPlayer: Player = entityDamageByEntityEvent.entity as Player
            val damagerPlayer: Player = entityDamageByEntityEvent.damager as Player

            val world: World = victimPlayer.world
            val worldName: String = world.name
            val spawnLocation: Location = worldConfig.getWorldSpawnLocation(worldName) ?: return

            if (victimPlayer.location.distance(spawnLocation) < 15.00) entityDamageByEntityEvent.isCancelled = true

            entityDamageByEntityEvent.damage = 0.00
            val lastDamageData: HashMap<Player, DamageData> = gameAssets.lastDamageData

            lastDamageData[victimPlayer] = DamageData(damagerPlayer, System.currentTimeMillis())
        }
    }
}