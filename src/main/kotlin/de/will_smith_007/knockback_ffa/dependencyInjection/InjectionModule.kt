package de.will_smith_007.knockback_ffa.dependencyInjection

import com.google.inject.AbstractModule
import de.will_smith_007.knockback_ffa.fileConfig.KnockbackConfig
import de.will_smith_007.knockback_ffa.fileConfig.interfaces.IDatabaseConfig
import de.will_smith_007.knockback_ffa.fileConfig.interfaces.IWorldConfig
import de.will_smith_007.knockback_ffa.scoreboard.ScoreboardManager
import de.will_smith_007.knockback_ffa.scoreboard.interfaces.IScoreboardManager
import org.bukkit.plugin.java.JavaPlugin

class InjectionModule(
    private var javaPlugin: JavaPlugin
) : AbstractModule() {

    override fun configure() {
        bind(JavaPlugin::class.java).toInstance(javaPlugin)
        bind(IDatabaseConfig::class.java).to(KnockbackConfig::class.java)
        bind(IWorldConfig::class.java).to(KnockbackConfig::class.java)
        bind(IScoreboardManager::class.java).to(ScoreboardManager::class.java)
    }
}