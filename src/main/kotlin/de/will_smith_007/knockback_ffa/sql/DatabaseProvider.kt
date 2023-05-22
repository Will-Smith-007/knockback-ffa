package de.will_smith_007.knockback_ffa.sql

import com.google.inject.Inject
import com.zaxxer.hikari.HikariDataSource
import de.will_smith_007.knockback_ffa.fileConfig.interfaces.IDatabaseConfig
import de.will_smith_007.knockback_ffa.sql.interfaces.IDatabaseConnector
import de.will_smith_007.knockback_ffa.sql.interfaces.IDatabaseProvider
import de.will_smith_007.knockback_ffa.sql.interfaces.IHikariConfigurationHandler
import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.concurrent.CompletableFuture
import java.util.logging.Logger

class DatabaseProvider @Inject constructor(
    private val databaseConfig: IDatabaseConfig,
    javaPlugin: JavaPlugin,
    private val hikariConfigurationHandler: IHikariConfigurationHandler,
    private val logger: Logger = javaPlugin.logger,
    private val hikariDataSource: HikariDataSource? = hikariConfigurationHandler.getHikariDataSource(),
    private var connection: Connection? = null
): IDatabaseConnector, IDatabaseProvider {

    override fun connect() {
        try {
            if (hikariDataSource == null) return
            connection = hikariDataSource.connection

            logger.info("Connection to the sql database was established.")
        } catch (sqlException: SQLException) {
            sqlException.printStackTrace()
        }
    }

    override fun closeConnection() {
        if (connection?.isClosed == true) return
    }

    override fun updateQuery(sqlQuery: String) {
        TODO("Not yet implemented")
    }

    override fun preparedStatement(sqlQuery: String): PreparedStatement {
        TODO("Not yet implemented")
    }

    override fun prepareStatementAsync(sqlQuery: String): CompletableFuture<PreparedStatement> {
        TODO("Not yet implemented")
    }

    fun createDefaultTables() {

    }
}