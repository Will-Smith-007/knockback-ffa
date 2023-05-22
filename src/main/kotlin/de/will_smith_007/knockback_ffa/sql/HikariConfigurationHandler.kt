package de.will_smith_007.knockback_ffa.sql

import com.google.inject.Inject
import com.google.inject.Singleton
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import de.will_smith_007.knockback_ffa.fileConfig.interfaces.IDatabaseConfig
import de.will_smith_007.knockback_ffa.sql.interfaces.IHikariConfigurationHandler

@Singleton
class HikariConfigurationHandler @Inject constructor(
    private val databaseConfig: IDatabaseConfig
) : IHikariConfigurationHandler {

    override fun getHikariDataSource(): HikariDataSource? {
        val hikariConfig = HikariConfig()
        val databaseName: String = databaseConfig.getSQLDatabaseName() ?: return null

        hikariConfig.jdbcUrl = "jdbc:mysql://${databaseConfig.getSQLHost()}:${databaseConfig.getSQLPort()}/" +
                databaseName

        hikariConfig.username = databaseConfig.getSQLUsername()
        hikariConfig.password = databaseConfig.getSQLSecret()

        hikariConfig.connectionTestQuery = "SELECT 1"

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useUnicode", "true");
        hikariConfig.addDataSourceProperty("maxIdleTime", 28800);

        hikariConfig.poolName = databaseName
        hikariConfig.maximumPoolSize = 2
        hikariConfig.minimumIdle = 5

        return HikariDataSource(hikariConfig)
    }
}