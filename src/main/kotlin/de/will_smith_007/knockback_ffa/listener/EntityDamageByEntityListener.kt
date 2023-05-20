package de.will_smith_007.knockback_ffa.listener

import com.google.inject.Inject
import de.will_smith_007.knockback_ffa.damage_data.DamageData
import de.will_smith_007.knockback_ffa.game_assets.GameAssets
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class EntityDamageByEntityListener @Inject constructor(
    private val gameAssets: GameAssets
): Listener {

    @EventHandler
    fun onEntityDamageByEntity(entityDamageByEntityEvent: EntityDamageByEntityEvent) {
        if (entityDamageByEntityEvent.damager is Player && entityDamageByEntityEvent.entity is Player) {
            entityDamageByEntityEvent.damage = 0.00

            val victimPlayer: Player = entityDamageByEntityEvent.entity as Player
            val damagerPlayer: Player = entityDamageByEntityEvent.damager as Player
            val lastDamageData: HashMap<Player, DamageData> = gameAssets.lastDamageData

            lastDamageData[victimPlayer] = DamageData(damagerPlayer, System.currentTimeMillis())
        }
    }
}