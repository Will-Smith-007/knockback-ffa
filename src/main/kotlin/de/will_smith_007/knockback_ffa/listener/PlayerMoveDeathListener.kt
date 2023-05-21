package de.will_smith_007.knockback_ffa.listener

import com.google.inject.Inject
import de.will_smith_007.knockback_ffa.game_data.GameData
import de.will_smith_007.knockback_ffa.damage_data.DamageData
import de.will_smith_007.knockback_ffa.file_config.KnockbackConfig
import de.will_smith_007.knockback_ffa.game_assets.GameAssets
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class PlayerMoveDeathListener @Inject constructor(
    private val gameAssets: GameAssets,
    private val knockbackConfig: KnockbackConfig
) : Listener {

    @EventHandler
    fun onPlayerMove(playerMoveEvent: PlayerMoveEvent) {
        val player: Player = playerMoveEvent.player
        val gameData: GameData = gameAssets.gameData ?: return

        val world: World = gameData.world
        val worldName: String = world.name
        val deathHeight: Int = knockbackConfig.getDeathHeight(worldName)
        val playerLocation: Location = player.location

        if (playerLocation.blockY > deathHeight) return

        val worldSpawnLocation: Location = knockbackConfig.getWorldSpawnLocation(worldName) ?: return
        val lastDamageData: HashMap<Player, DamageData> = gameAssets.lastDamageData
        val damageData: DamageData? = lastDamageData[player]
        val currentTimeMillis: Long = System.currentTimeMillis()

        if (damageData == null || (currentTimeMillis - damageData.lastDamageMillis) > 5000) {
            player.showTitle(
                Title.title(
                    Component.text("✘ You died ✘", NamedTextColor.RED),
                    Component.text(player.name, NamedTextColor.AQUA)
                )
            )
        } else {
            val damagePlayer: Player = damageData.lastDamagePlayer

            player.showTitle(
                Title.title(
                    Component.text("✘ You died ✘", NamedTextColor.RED),
                    Component.text(damagePlayer.name, NamedTextColor.AQUA)
                )
            )
            player.playSound(playerLocation, Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f)

            damagePlayer.showTitle(
                Title.title(
                    Component.text("✔ You killed ✔", NamedTextColor.GREEN),
                    Component.text(player.name, NamedTextColor.AQUA)
                )
            )
            damagePlayer.playSound(damagePlayer.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 0.0f)
        }

        player.teleport(worldSpawnLocation)
    }
}