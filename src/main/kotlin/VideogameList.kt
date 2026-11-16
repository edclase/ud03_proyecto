package esq.dam

import java.sql.SQLException
import java.sql.Statement

data class VideogameList(val id: Int? = null, val playerId: Int)

data class ListVideogame(val listId: Int, val videogameId: Int)

object ListDAO {

    fun insert(playerId: Int): Int? {
        DatabaseManager.getConnection()?.use { conn ->
            conn.prepareStatement("SELECT id FROM players WHERE id = ?").use { statement ->
                statement.setInt(1, playerId)
                if (!statement.executeQuery().next()) return null
            }
            val sql = "INSERT INTO lists (player_id) VALUES (?)"
            conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { statement ->
                statement.setInt(1, playerId)
                if (statement.executeUpdate() > 0) {
                    statement.generatedKeys.use { rs ->
                        if (rs.next()) return rs.getInt(1)
                    }
                }
            }
        }
        return null
    }

    fun delete(listId: Int): Boolean {
        val sql = "DELETE FROM lists WHERE id = ?"
        DatabaseManager.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { statement ->
                statement.setInt(1, listId)
                return statement.executeUpdate() > 0
            }
        }
        return false
    }
}

object ListVideogameDAO {

    fun insert(listVideogame: ListVideogame): Boolean {
        val conn = DatabaseManager.getConnection() ?: return false
        try {
            conn.autoCommit = false

            conn.prepareStatement("SELECT id FROM lists WHERE id = ?").use { statement ->
                statement.setInt(1, listVideogame.listId)
                if (!statement.executeQuery().next()) {
                    println("La lista ${listVideogame.listId} no existe.")
                    conn.rollback()
                    return false
                }
            }

            conn.prepareStatement("SELECT id FROM videogames WHERE id = ?").use { statement ->
                statement.setInt(1, listVideogame.videogameId)
                if (!statement.executeQuery().next()) {
                    println("El videojuego ${listVideogame.videogameId} no existe.")
                    conn.rollback()
                    return false
                }
            }

            val sql = "INSERT INTO videogames_list (list_id, videogame_id) VALUES (?, ?)"
            conn.prepareStatement(sql).use { statement ->
                statement.setInt(1, listVideogame.listId)
                statement.setInt(2, listVideogame.videogameId)
                statement.executeUpdate()
            }

            conn.commit()
            return true
        } catch (e: SQLException) {
            conn.rollback()
            println("Error en la transaccion, se deshacen los cambios: ${e.message}")
            return false
        } finally {
            conn.autoCommit = true
            DatabaseManager.closeConnection(conn)
        }
    }

    fun getVideogamesFromList(listId: Int): List<Videogame> {
        val list = mutableListOf<Videogame>()
        val sql = """
            SELECT vg.* FROM videogames vg
            JOIN videogames_list vl ON vl.videogame_id = vg.id
            WHERE vl.list_id = ?
        """.trimIndent()
        DatabaseManager.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { statement ->
                statement.setInt(1, listId)
                val rs = statement.executeQuery()
                while (rs.next()) {
                    list.add(VideogamesDAO.readVideogame(rs))
                }
            }
        }
        return list
    }
}
