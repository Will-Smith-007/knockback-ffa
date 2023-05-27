package de.will_smith_007.knockback_ffa.sql.interfaces

import com.zaxxer.hikari.HikariDataSource

fun interface HikariConfigurationHandler {

    fun getHikariDataSource(): HikariDataSource
}