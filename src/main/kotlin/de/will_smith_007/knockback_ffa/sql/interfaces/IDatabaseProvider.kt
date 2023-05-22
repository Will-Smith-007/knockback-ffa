package de.will_smith_007.knockback_ffa.sql.interfaces

import java.sql.PreparedStatement
import java.util.concurrent.CompletableFuture

interface IDatabaseProvider {

    fun updateQuery(sqlQuery: String)

    fun preparedStatement(sqlQuery: String): PreparedStatement

    fun prepareStatementAsync(sqlQuery: String): CompletableFuture<PreparedStatement>
}