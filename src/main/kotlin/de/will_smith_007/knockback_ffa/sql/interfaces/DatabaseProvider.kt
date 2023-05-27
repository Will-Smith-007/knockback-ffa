package de.will_smith_007.knockback_ffa.sql.interfaces

import java.sql.PreparedStatement

interface DatabaseProvider {

    fun updateQuery(sqlQuery: String)

    fun preparedStatement(sqlQuery: String): PreparedStatement?
}