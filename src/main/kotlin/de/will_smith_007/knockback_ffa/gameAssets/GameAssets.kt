package de.will_smith_007.knockback_ffa.gameAssets

import com.google.inject.Singleton
import de.will_smith_007.knockback_ffa.damageData.DamageData
import de.will_smith_007.knockback_ffa.gameData.GameData
import org.bukkit.entity.Player

@Singleton
class GameAssets {

    val lastDamageData: HashMap<Player, DamageData> = HashMap()
    var gameData: GameData? = null
}