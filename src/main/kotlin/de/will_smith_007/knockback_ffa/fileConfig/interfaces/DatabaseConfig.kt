package de.will_smith_007.knockback_ffa.fileConfig.interfaces

interface DatabaseConfig {

    /**
     * Gets the configured hostname from the config file.
     * @return The configured hostname.
     */
    fun getSQLHost(): String?

    /**
     * Gets the configured port from the config file.
     * @return The configured port number.
     */
    fun getSQLPort(): Int

    /**
     * Gets the configured name of database from the config file.
     * @return The configured name of database which should be used.
     */
    fun getSQLDatabaseName(): String?

    /**
     * Gets the configured username from the config file.
     * @return The configured username.
     */
    fun getSQLUsername(): String?

    /**
     * Gets the configured password from the config file.
     * @return The configured password.
     */
    fun getSQLSecret(): String?
}