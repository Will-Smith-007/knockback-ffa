package de.will_smith_007.knockback_ffa.game_assets

import com.google.inject.Singleton
import de.will_smith_007.knockback_ffa.game_data.GameData
import de.will_smith_007.knockback_ffa.damage_data.DamageData
import org.bukkit.entity.Player

@Singleton
class GameAssets {

    val lastDamageData: HashMap<Player, DamageData> = HashMap()
    var gameData: GameData? = null
}