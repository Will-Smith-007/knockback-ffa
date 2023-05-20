package de.will_smith_007.knockback_ffa.dependency_injection

import com.google.inject.AbstractModule
import de.will_smith_007.knockback_ffa.file_config.KnockbackConfig
import de.will_smith_007.knockback_ffa.file_config.interfaces.IDatabaseConfig
import de.will_smith_007.knockback_ffa.file_config.interfaces.IWorldConfig
import org.bukkit.plugin.java.JavaPlugin

class InjectionModule(
    private var javaPlugin: JavaPlugin
) : AbstractModule() {

    override fun configure() {
        bind(JavaPlugin::class.java).toInstance(javaPlugin)
        bind(IDatabaseConfig::class.java).to(KnockbackConfig::class.java)
        bind(IWorldConfig::class.java).to(KnockbackConfig::class.java)
    }
}