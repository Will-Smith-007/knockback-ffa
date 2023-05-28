package de.will_smith_007.knockback_ffa.sql.interfaces

/**
 * This interface has only the responsibility to create or close database connections.
 */
interface DatabaseConnector {

    /**
     * Tries to establish a connection to the database.
     */
    fun connect()

    /**
     * Tries to close an established database connection.
     */
    fun closeConnection()
}