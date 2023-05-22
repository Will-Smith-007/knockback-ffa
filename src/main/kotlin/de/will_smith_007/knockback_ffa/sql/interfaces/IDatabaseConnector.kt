package de.will_smith_007.knockback_ffa.sql.interfaces

interface IDatabaseConnector {

    fun connect()

    fun closeConnection()
}