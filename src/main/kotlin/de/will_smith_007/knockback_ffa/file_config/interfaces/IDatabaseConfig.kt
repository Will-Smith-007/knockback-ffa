package de.will_smith_007.knockback_ffa.file_config.interfaces

interface IDatabaseConfig {

    fun getSQLHost(): String?

    fun getSQLPort(): Int

    fun getSQLDatabaseName(): String?

    fun getSQLUsername(): String?

    fun getSQLSecret(): String?
}