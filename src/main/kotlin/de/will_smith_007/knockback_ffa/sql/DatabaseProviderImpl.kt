package de.will_smith_007.knockback_ffa.sql

import com.google.inject.Inject
import com.google.inject.Singleton
import com.zaxxer.hikari.HikariDataSource
import de.will_smith_007.knockback_ffa.sql.interfaces.DatabaseConnector
import de.will_smith_007.knockback_ffa.sql.interfaces.DatabaseProvider
import de.will_smith_007.knockback_ffa.sql.interfaces.HikariConfigurationHandler
import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.logging.Logger

@Singleton
class DatabaseProviderImpl @Inject constructor(
    javaPlugin: JavaPlugin,
    hikariConfigurationHandler: HikariConfigurationHandler
) : DatabaseConnector, DatabaseProvider {

    private val logger: Logger = javaPlugin.logger
    private val hikariDataSource: HikariDataSource = hikariConfigurationHandler.getHikariDataSource()
    private var connection: Connection? = null

    override fun connect() {
        try {
            connection = hikariDataSource.connection

            logger.info("Connection to the sql database was established.")
            createDefaultTables()
        } catch (sqlException: SQLException) {
            sqlException.printStackTrace()
        }
    }

    override fun closeConnection() {
        if (connection?.isClosed == true) return
        connection?.close()
        logger.info("Database connection was closed.")
    }

    override fun updateQuery(sqlQuery: String) {
        establishConnectionIfClosed()
        connection?.createStatement().use {
            it?.executeUpdate(sqlQuery)
        }
    }

    override fun preparedStatement(sqlQuery: String): PreparedStatement? {
        establishConnectionIfClosed()
        return connection?.prepareStatement(sqlQuery)
    }

    private fun createDefaultTables() {
        updateQuery(
            "CREATE TABLE IF NOT EXISTS knockback(uuid VARCHAR(64) PRIMARY KEY, " +
                    "kills INT DEFAULT 0, deaths INT DEFAULT 0);"
        )
    }

    private fun establishConnectionIfClosed() {
        if (connection?.isClosed == true) connect()
    }
}