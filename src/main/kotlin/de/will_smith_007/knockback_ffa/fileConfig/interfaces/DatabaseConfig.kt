package de.will_smith_007.knockback_ffa.fileConfig.interfaces

interface DatabaseConfig {

    fun getSQLHost(): String?

    fun getSQLPort(): Int

    fun getSQLDatabaseName(): String?

    fun getSQLUsername(): String?

    fun getSQLSecret(): String?
}