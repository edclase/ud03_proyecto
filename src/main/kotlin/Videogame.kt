package esq.dam

import java.sql.ResultSet
import java.sql.Statement

data class Videogame(
    val id: Int? = null,
    val title: String,
    val genre: String,
    val year: Int,
    val grade: Double,
)

object VideogamesDAO {

    fun readVideogame(rs: ResultSet): Videogame {
        return Videogame(
            id = rs.getInt("id"),
            title = rs.getString("title"),
            genre = rs.getString("genre"),
            year = rs.getInt("year_int"),
            grade = rs.getDouble("grade"),
        )
    }

    fun listGames(): List<Videogame> {
        val list = mutableListOf<Videogame>()
        val sql = "SELECT * FROM videogames"
        DatabaseManager.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { statement ->
                val rs = statement.executeQuery()
                while (rs.next()) {
                    list.add(readVideogame(rs))
                }
            }
        }
        return list
    }

    fun findById(id: Int): Videogame? {
        val sql = "select * from videogames vg WHERE vg.id = ?"
        DatabaseManager.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { statement ->
                statement.setInt(1, id)
                val rs = statement.executeQuery()
                if (rs.next()) {
                    return readVideogame(rs)
                }
            }
        }
        return null
    }

    fun insertVideogame(videogame: Videogame): Int? {
        DatabaseManager.getConnection()?.use { conn ->
            val sql = "INSERT INTO videogames(title, genre, year_int, grade) VALUES (?, ?, ?, ?)"
            conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { statement ->
                statement.setString(1, videogame.title)
                statement.setString(2, videogame.genre)
                statement.setInt(3, videogame.year)
                statement.setDouble(4, videogame.grade)
                if (statement.executeUpdate() > 0) {
                    statement.generatedKeys.use { rs ->
                        if (rs.next()) return rs.getInt(1)
                    }
                }
            }
        }
        return null
    }

    fun updateVideogame(videogame: Videogame): Boolean {
        val id = videogame.id
        if (id == null) {
            println("Error: no se puede actualizar un videojuego sin ID.")
            return false
        }
        DatabaseManager.getConnection()?.use { conn ->
            val sql = "UPDATE videogames SET title = ?, genre = ?, year_int = ?, grade = ? WHERE id = ?"
            conn.prepareStatement(sql).use { statement ->
                statement.setString(1, videogame.title)
                statement.setString(2, videogame.genre)
                statement.setInt(3, videogame.year)
                statement.setDouble(4, videogame.grade)
                statement.setInt(5, id)
                return statement.executeUpdate() > 0
            }
        }
        return false
    }

    fun deleteVideogame(id: Int): Boolean {
        val sql = "DELETE FROM videogames WHERE id = ?"
        DatabaseManager.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { statement ->
                statement.setInt(1, id)
                return statement.executeUpdate() > 0
            }
        }
        return false
    }

}
