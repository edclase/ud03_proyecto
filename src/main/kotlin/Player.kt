package esq.dam

import java.sql.Statement

data class Player(val id: Int? = null, val username: String, val email: String)

object PlayerDAO {

    fun insert(player: Player): Int? {
        val sql = "INSERT INTO players (NAME, mail) VALUES (?, ?)"
        DatabaseManager.getConnection()?.use { conn ->
            conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { statement ->
                statement.setString(1, player.username)
                statement.setString(2, player.email)
                if (statement.executeUpdate() > 0) {
                    statement.generatedKeys.use { rs ->
                        if (rs.next()) return rs.getInt(1)
                    }
                }
            }
        }
        return null
    }

    fun delete(playerId: Int): Boolean {
        val sql = "DELETE FROM players WHERE id = ?"
        DatabaseManager.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { statement ->
                statement.setInt(1, playerId)
                return statement.executeUpdate() > 0
            }
        }
        return false
    }
}
