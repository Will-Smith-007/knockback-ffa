package de.will_smith_007.knockback_ffa.sql.interfaces

import java.sql.PreparedStatement

interface DatabaseProvider {

    /**
     * Tries to executes a sql update query.
     * @param sqlQuery Sql update query which should be executed.
     */
    fun updateQuery(sqlQuery: String)

    /**
     * Prepares a statement with the specified sql query.
     * @param sqlQuery Sql query which should be prepared.
     */
    fun preparedStatement(sqlQuery: String): PreparedStatement?
}