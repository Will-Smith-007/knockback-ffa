package de.will_smith_007.knockback_ffa.sql.interfaces

interface DatabaseConnector {

    fun connect()

    fun closeConnection()
}