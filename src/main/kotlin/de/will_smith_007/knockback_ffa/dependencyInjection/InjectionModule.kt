package de.will_smith_007.knockback_ffa.dependencyInjection

import com.google.inject.AbstractModule
import de.will_smith_007.knockback_ffa.fileConfig.KnockbackConfig
import de.will_smith_007.knockback_ffa.fileConfig.interfaces.DatabaseConfig
import de.will_smith_007.knockback_ffa.fileConfig.interfaces.WorldConfig
import de.will_smith_007.knockback_ffa.scoreboard.ScoreboardManagerImpl
import de.will_smith_007.knockback_ffa.scoreboard.interfaces.ScoreboardManager
import de.will_smith_007.knockback_ffa.sql.DatabaseProviderImpl
import de.will_smith_007.knockback_ffa.sql.DatabaseStatsImpl
import de.will_smith_007.knockback_ffa.sql.HikariConfigurationHandlerImpl
import de.will_smith_007.knockback_ffa.sql.interfaces.DatabaseConnector
import de.will_smith_007.knockback_ffa.sql.interfaces.DatabaseProvider
import de.will_smith_007.knockback_ffa.sql.interfaces.DatabaseStats
import de.will_smith_007.knockback_ffa.sql.interfaces.HikariConfigurationHandler
import org.bukkit.plugin.java.JavaPlugin

class InjectionModule(
    private var javaPlugin: JavaPlugin
) : AbstractModule() {

    override fun configure() {
        bind(JavaPlugin::class.java).toInstance(javaPlugin)
        bind(DatabaseConfig::class.java).to(KnockbackConfig::class.java)
        bind(WorldConfig::class.java).to(KnockbackConfig::class.java)
        bind(ScoreboardManager::class.java).to(ScoreboardManagerImpl::class.java)
        bind(DatabaseProvider::class.java).to(DatabaseProviderImpl::class.java)
        bind(HikariConfigurationHandler::class.java).to(HikariConfigurationHandlerImpl::class.java)
        bind(DatabaseStats::class.java).to(DatabaseStatsImpl::class.java)
        bind(DatabaseConnector::class.java).to(DatabaseProviderImpl::class.java)
    }
}