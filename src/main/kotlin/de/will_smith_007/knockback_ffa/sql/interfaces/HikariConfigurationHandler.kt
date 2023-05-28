package de.will_smith_007.knockback_ffa.sql.interfaces

import com.zaxxer.hikari.HikariDataSource

/**
 * This interface has only the responsibility to prepare the [HikariDataSource].
 */
fun interface HikariConfigurationHandler {

    /**
     * Prepares the [HikariDataSource] based on the Hikari configuration.
     */
    fun getHikariDataSource(): HikariDataSource
}